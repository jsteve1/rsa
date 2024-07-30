package utils;

import java.math.BigInteger;

public class MillerRabin implements PrimeChecker {

    /**
     * Check if a number is prime using Miller-Rabin primality test method.
     * @param n - The number to check for primality, as a BigInteger.
     * @param iterations - The number of iterations to run the test, which determines the accuracy of the test. Higher iterations, higher accuracy.
     * @return - false if n is composite, true if n is probably prime.
     */
    @Override
    public boolean isPrime(BigInteger n, int iterations) {
        if (n.compareTo(BigInteger.ONE) <= 0 || n.equals(BigInteger.valueOf(4))) return false;
        if (n.compareTo(BigInteger.valueOf(3)) <= 0) return true;

        BigInteger d = n.subtract(BigInteger.ONE);
        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.TWO);
        }

        for (int i = 0; i < iterations; i++) {
            if (!this.millerRabinTest(d, n)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates a prime number of a specified bit length.
     * @param bitLength - The bit length of the prime number to generate.
     * @param certainty - A measure of the certainty that the returned number is prime.
     * @return A prime number of the specified bit length.
     */
    @Override
    public BigInteger generatePrime(int bitLength, int certainty) {
        BigInteger primeCandidate;

        // Generate a random prime candidate and test for primality
        do {
            primeCandidate = new BigInteger(bitLength, secureRandom);
            // Ensure the candidate is odd to increase efficiency
            if (!primeCandidate.testBit(0)) {
                primeCandidate = primeCandidate.add(BigInteger.ONE);
            }
        } while (!this.isPrime(primeCandidate, certainty));

        return primeCandidate;
    }

    
    /**
     * Performs the Miller-Rabin primality test on a given number.
     * @param d - The exponent derived from n-1 where n = 2^d * r + 1.
     * @param n - The number to test for primality.
     * @return true if n is probably prime, false otherwise.
     */
    private boolean millerRabinTest(BigInteger d, BigInteger n) {
        BigInteger a = PrimeChecker.getRandomBigIntegerInRange(BigInteger.TWO, n.subtract(BigInteger.TWO));
        BigInteger x = a.modPow(d, n);

        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
            return true;
        }

        while (!d.equals(n.subtract(BigInteger.ONE))) {
            x = x.multiply(x).mod(n);
            d = d.multiply(BigInteger.TWO);

            if (x.equals(BigInteger.ONE)) return false;
            if (x.equals(n.subtract(BigInteger.ONE))) return true;
        }
        return false;
    }
}
