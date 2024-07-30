package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.Test;

import utils.Fermats; // Assuming this is your Fermat's test implementation
import utils.PrimeChecker;

import java.math.BigInteger;

/**
 * Test suite for {@link FermatsTest} focusing on its capability to accurately
 * determine the primality of given numbers through Fermat's Little Theorem.
 */
public class FermatsTest {
    private final PrimeChecker fermatsChecker = new Fermats();

    @Test
    public void testSmallPrimes() {
        assertTrue("2 should be prime", fermatsChecker.isPrime(new BigInteger("2"), 40));
        assertTrue("3 should be prime", fermatsChecker.isPrime(new BigInteger("3"), 40));
        assertTrue("5 should be prime", fermatsChecker.isPrime(new BigInteger("5"), 40));
        assertTrue("7 should be prime", fermatsChecker.isPrime(new BigInteger("7"), 40));
    }

    @Test
    public void testSmallNonPrimes() {
        assertFalse("4 should not be prime", fermatsChecker.isPrime(new BigInteger("4"), 40));
        assertFalse("1 should not be prime", fermatsChecker.isPrime(new BigInteger("1"), 40));
        assertFalse("0 should not be prime", fermatsChecker.isPrime(new BigInteger("0"), 40));
        assertFalse("-1 should not be prime", fermatsChecker.isPrime(new BigInteger("-1"), 40));
    }

    @Test
    public void testLargePrime() {
        assertTrue("104729 should be prime", fermatsChecker.isPrime(new BigInteger("104729"), 40));
        assertTrue("1299709 should be prime", fermatsChecker.isPrime(new BigInteger("1299709"), 40));
    }

    @Test
    public void testLargeNonPrime() {
        assertFalse("104730 should not be prime", fermatsChecker.isPrime(new BigInteger("104730"), 40));
        assertFalse("1299710 should not be prime", fermatsChecker.isPrime(new BigInteger("1299710"), 40));
    }

    @Test
    public void testCrazyHighPrime() {
        assertTrue("The largest 32-bit prime should be prime", fermatsChecker.isPrime(new BigInteger("2147483647"), 40));
        assertTrue("A large known prime should be prime", fermatsChecker.isPrime(new BigInteger("32416190071"), 40));
        assertTrue("A very large known prime should be prime", fermatsChecker.isPrime(new BigInteger("68078235559159578833"), 40));
    }

    @Test
    public void testVarietyOfIterations() {
        assertTrue("23 should be prime with 1 iteration", fermatsChecker.isPrime(new BigInteger("23"), 1));
        assertTrue("23 should be prime with 100 iterations", fermatsChecker.isPrime(new BigInteger("23"), 100));
        assertFalse("25 should not be prime with 10 iterations", fermatsChecker.isPrime(new BigInteger("25"), 10));
        assertFalse("25 should not be prime with 100 iterations", fermatsChecker.isPrime(new BigInteger("25"), 100));
        assertTrue("7 should be prime with 1 iteration", fermatsChecker.isPrime(new BigInteger("7"), 1));
        assertTrue("7 should be prime with 9000 iterations", fermatsChecker.isPrime(new BigInteger("7"), 9000));
    }

    @Test
    public void benchmarkIsPrime() {
        Logger logger = LoggerFactory.getLogger(FermatsTest.class);
        BigInteger[] numbersToTest = {
            BigInteger.valueOf(2),
            BigInteger.valueOf(104729),
            new BigInteger("32416190071"),
            BigInteger.valueOf(4),
            BigInteger.valueOf(104730),
            new BigInteger("32416190072")
        };
        int iterations = 40;

        for (BigInteger number : numbersToTest) {
            long startTime = System.nanoTime();
            boolean isPrime = fermatsChecker.isPrime(number, iterations);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            logger.info(() -> "Testing " + number + " (isPrime: " + isPrime + ") took " + duration + " nanoseconds.");
        }
    }
}
