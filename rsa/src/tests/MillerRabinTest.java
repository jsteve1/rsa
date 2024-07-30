package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.Test;

import utils.MillerRabin;
import utils.PrimeChecker;

import java.math.BigInteger;

/**
 * Test suite for {@link PrimalityUtils} focusing on its capability to accurately
 * determine the primality of given numbers through the Miller-Rabin primality test.
 */
public class MillerRabinTest {
    private final PrimeChecker millerRabinChecker = new MillerRabin();
    /**
     * Tests the primality check for small prime numbers.
     * These cases are expected to directly return true as these numbers are well-known primes.
     */
    @Test
    public void testSmallPrimes() {
        assertTrue("2 should be prime", millerRabinChecker.isPrime(new BigInteger("2"), 40));
        assertTrue("3 should be prime", millerRabinChecker.isPrime(new BigInteger("3"), 40));
        assertTrue("5 should be prime", millerRabinChecker.isPrime(new BigInteger("5"), 40));
        assertTrue("7 should be prime", millerRabinChecker.isPrime(new BigInteger("7"), 40));
    }

    /**
     * Tests the primality check for small non-prime numbers.
     * These cases are straightforward checks that should quickly determine the non-primality of the inputs.
     */
    @Test
    public void testSmallNonPrimes() {
        assertFalse("4 should not be prime", millerRabinChecker.isPrime(new BigInteger("4"), 40));
        assertFalse("1 should not be prime", millerRabinChecker.isPrime(new BigInteger("1"), 40));
        assertFalse("0 should not be prime", millerRabinChecker.isPrime(new BigInteger("0"), 40));
        assertFalse("-1 should not be prime", millerRabinChecker.isPrime(new BigInteger("-1"), 40));
    }

    /**
     * Tests the primality check for larger prime numbers.
     * Validates the algorithm's effectiveness and efficiency on more computationally intensive tasks.
     */
    @Test
    public void testLargePrime() {
        assertTrue("104729 should be prime", millerRabinChecker.isPrime(new BigInteger("104729"), 40));
        assertTrue("1299709 should be prime", millerRabinChecker.isPrime(new BigInteger("1299709"), 40));
    }

    /**
     * Tests the primality check for larger non-prime numbers.
     * These are important for ensuring the algorithm accurately identifies composite numbers in higher ranges.
     */
    @Test
    public void testLargeNonPrime() {
        assertFalse("104730 should not be prime", millerRabinChecker.isPrime(new BigInteger("104730"), 40));
        assertFalse("1299710 should not be prime", millerRabinChecker.isPrime(new BigInteger("1299710"), 40));
    }

    /**
     * Tests the primality check on very large prime numbers.
     * These cases challenge the algorithm's capability to handle extremely large inputs efficiently.
     */
    @Test
    public void testCrazyHighPrime() {
        assertTrue("The largest 32-bit prime should be prime", millerRabinChecker.isPrime(new BigInteger("2147483647"), 40));
        assertTrue("A large known prime should be prime", millerRabinChecker.isPrime(new BigInteger("32416190071"), 40));
        assertTrue("A very large known prime should be prime", millerRabinChecker.isPrime(new BigInteger("68078235559159578833"), 40));
    }

    /**
     * Tests the effect of varying the number of iterations on the primality test's accuracy.
     * This helps to understand how the accuracy of the test changes with more or fewer iterations.
     */
    @Test
    public void testVarietyOfIterations() {
        assertTrue("23 should be prime with 1 iteration", millerRabinChecker.isPrime(new BigInteger("23"), 1));
        assertTrue("23 should be prime with 100 iterations", millerRabinChecker.isPrime(new BigInteger("23"), 100));
        assertFalse("25 should not be prime with 10 iterations", millerRabinChecker.isPrime(new BigInteger("25"), 10));
        assertFalse("25 should not be prime with 100 iterations", millerRabinChecker.isPrime(new BigInteger("25"), 100));
        assertTrue("7 should be prime with 1 iteration", millerRabinChecker.isPrime(new BigInteger("7"), 1));
        assertTrue("7 should be prime with 9000 iterations", millerRabinChecker.isPrime(new BigInteger("7"), 9000));
    }

    /**
     * Benchmark test to measure the performance of the isPrime method across various numbers.
     * Utilizes a fixed number of iterations for the Miller-Rabin test and logs the time taken for each test.
     */
    @Test
    public void benchmarkIsPrime() {
        Logger logger = LoggerFactory.getLogger(MillerRabinTest.class);
        BigInteger[] numbersToTest = {
            BigInteger.valueOf(2), // small prime
            BigInteger.valueOf(104729), // medium prime
            new BigInteger("32416190071"), // large prime
            BigInteger.valueOf(4), // small non-prime
            BigInteger.valueOf(104730), // medium non-prime
            new BigInteger("32416190072") // large non-prime
        };
        int iterations = 40; // Number of iterations for the Miller-Rabin test

        for (BigInteger number : numbersToTest) {
            long startTime = System.nanoTime();
            boolean isPrime = millerRabinChecker.isPrime(number, iterations);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            logger.info(() -> "Testing " + number + " (isPrime: " + isPrime + ") took " + duration + " nanoseconds.");
        }
    }
}
