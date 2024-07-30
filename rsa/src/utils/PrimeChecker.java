package utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public interface PrimeChecker {

   static final SecureRandom secureRandom = new SecureRandom();

   /**
    * Check if a number is prime using an implemented primality test method.
    * @param n
    * @param iterations
    * @return
    */
   public boolean isPrime(BigInteger n, int iterations); 

    /**
     * Generates a prime number of a specified bit length.
     * @param bitLength - The bit length of the prime number to generate.
     * @param certainty - A measure of the certainty that the returned number is prime.
     * @return A prime number of the specified bit length.
     */
   public BigInteger generatePrime(int bitLength, int certainty);

    /**
     * Generates a random BigInteger within the range [min, max].
     * @param min - The minimum value in the range, inclusive.
     * @param max - The maximum value in the range, inclusive.
     * @return A uniformly distributed random BigInteger within the range [min, max].
     */
    public static BigInteger getRandomBigIntegerInRange(BigInteger min, BigInteger max) {
        if (min.compareTo(max) >= 0) {
            // Direct return if range is invalid or min equals max
            return min;
        }
    
        BigInteger range = max.subtract(min).add(BigInteger.ONE);
        int minLength = range.bitLength(); // Use range's bitLength to minimize overhead
    
        BigInteger result;
        do {
            result = new BigInteger(minLength, secureRandom);
        } while (result.compareTo(range) >= 0);
    
        return result.add(min);
    } 
}