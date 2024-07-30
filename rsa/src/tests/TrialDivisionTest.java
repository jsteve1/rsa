package tests;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import utils.TrialDivision;

import java.math.BigInteger;

public class TrialDivisionTest {

    private final TrialDivision trialDivision = new TrialDivision();

    @Test
    public void testIsPrime() {
        // Test with known primes
        assertTrue("2 should be prime", trialDivision.isPrime(new BigInteger("2"), 0));
        assertTrue("3 should be prime", trialDivision.isPrime(new BigInteger("3"), 0));
        assertTrue("5 should be prime", trialDivision.isPrime(new BigInteger("5"), 0));
        
        // Test with known non-primes
        assertFalse("4 should not be prime", trialDivision.isPrime(new BigInteger("4"), 0));
        assertFalse("6 should not be prime", trialDivision.isPrime(new BigInteger("6"), 0));
        assertFalse("1 should not be prime", trialDivision.isPrime(BigInteger.ONE, 0));
        assertFalse("0 should not be prime", trialDivision.isPrime(BigInteger.ZERO, 0));
        assertFalse("-1 should not be prime", trialDivision.isPrime(new BigInteger("-1"), 0));
        
        // Test with a larger prime and non-prime
        assertTrue("104729 should be prime", trialDivision.isPrime(new BigInteger("104729"), 0));
        assertFalse("104730 should not be prime", trialDivision.isPrime(new BigInteger("104730"), 0));
    }

    @Test
    public void testGeneratePrime() {
        // Generate a prime of a specified bit length and test its primality
        BigInteger prime = trialDivision.generatePrime(55, 10);
        assertTrue("Generated prime should be prime", trialDivision.isPrime(prime, 0));
        
        // Ensure the bit length is as requested (might be 1 bit shorter due to how primes are distributed)
        assertTrue("Generated prime bit length should be correct", prime.bitLength() == 55 || prime.bitLength() == 54);
    }

    @Test
    public void testPrimeCheckPerformance() {
        // Large prime for performance testing
        BigInteger largePrime = new BigInteger("32416190071"); // 11-digit prime

        long startTime = System.currentTimeMillis();
        boolean isPrime = trialDivision.isPrime(largePrime, 0);
        long endTime = System.currentTimeMillis();

        assertTrue("Large prime should be identified as prime", isPrime);

        long duration = endTime - startTime;
        System.out.println("Checking primality of a large prime took: " + duration + " ms");

        // Expect this to take significantly longer for large numbers, illustrating inefficiency
    }
    @Test
    public void testGeneratePrimePerformance() {
        int bitLength = 32; // Smaller bit length to keep test duration reasonable

        long startTime = System.currentTimeMillis();
        BigInteger prime = trialDivision.generatePrime(bitLength, 10);
        long endTime = System.currentTimeMillis();

        assertTrue("Generated number should be prime", trialDivision.isPrime(prime, 0));

        long duration = endTime - startTime;
        System.out.println("Generating a prime of bit length " + bitLength + " took: " + duration + " ms");

        // As bit length increases, the duration will increase significantly, demonstrating scalability issues
    }
}