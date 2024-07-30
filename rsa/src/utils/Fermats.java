package utils;

import java.math.BigInteger;

public class Fermats implements PrimeChecker {
    /**
     * Checks if a number is probably prime using Fermat's Little Theorem.
     *
     * @param n          the number to check for primality
     * @param iterations the number of iterations to perform the test
     * @return true if the number is probably prime, false otherwise
     */
    @Override
    public boolean isPrime(BigInteger n, int iterations) {
        if (n.compareTo(BigInteger.TWO) < 0) return false;
        if (n.equals(BigInteger.TWO)) return true;
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) return false;

        for (int i = 0; i < iterations; i++) {
            // Choose a randomly in the range [2, n - 2]
            BigInteger a = PrimeChecker.getRandomBigIntegerInRange(BigInteger.TWO, n.subtract(BigInteger.TWO));

            // a^(n-1) mod n should be 1
            if (!a.modPow(n.subtract(BigInteger.ONE), n).equals(BigInteger.ONE)) {
                return false;
            }
        }

        return true; // Passed all tests
    }

    /**
     * Generates a prime number within the specified range using Fermat's primality test.
     *
     * @param min the minimum bound of the range (inclusive).
     * @param max the maximum bound of the range (inclusive).
     * @return a prime number within the specified range.
     */
    @Override
    public BigInteger generatePrime(int bitLength, int certainty) {        
        BigInteger candidate;
        do {
            candidate = new BigInteger(bitLength, secureRandom);
            // Ensure the candidate is odd to increase efficiency
            if (!candidate.testBit(0)) {
                candidate = candidate.add(BigInteger.ONE);
            }
        } while (!this.isPrime(candidate, certainty));
        return candidate;
    }
}