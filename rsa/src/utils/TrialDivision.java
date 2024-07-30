package utils;

import java.math.BigInteger;

/**
 * The TrialDivision class implements the PrimeChecker interface and provides methods for checking primality
 * and generating prime numbers using the trial division algorithm.
 */
public class TrialDivision implements PrimeChecker {
    /**
     * Checks if a given number is prime using the trial division algorithm.
     *
     * @param n          the number to check for primality
     * @param iterations NOT USED HERE
     * @return true if the number is prime, false otherwise
     */
    @Override
    public boolean isPrime(BigInteger n, int iterations) {
        if (n.compareTo(BigInteger.ONE) <= 0) {
            return false;
        }
        if (n.compareTo(BigInteger.TWO) == 0) {
            return true;
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return false;
        }
        BigInteger sqrtN = n.sqrt();
        for (BigInteger i = BigInteger.valueOf(3); i.compareTo(sqrtN) <= 0; i = i.add(BigInteger.TWO)) {
            if (n.mod(i).equals(BigInteger.ZERO)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Generates a prime number with the specified bit length and certainty.
     *
     * @param bitLength  the bit length of the generated prime number
     * @param certainty  the certainty of the generated prime number
     * @return a prime number with the specified bit length and certainty
     */
    @Override
    public BigInteger generatePrime(int bitLength, int certainty) {
        BigInteger prime;
        do {
            prime = new BigInteger(bitLength, PrimeChecker.secureRandom);
        } while (!isPrime(prime, certainty));
        return prime;
    }
}
