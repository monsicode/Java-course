package bg.sofia.uni.fmi.mjt.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NumberUtilsTest {

    @Test
    void testIsPrimeWithNegativePrime() {
        NumberUtils numberUtils = new NumberUtils();

      assertFalse(numberUtils.isPrime(-2),"isPrime should return false");

    }


    @Test
    void testIsPrimeWithLessThanTwo() {
        NumberUtils numberUtils = new NumberUtils();

        assertThrows(IllegalArgumentException.class, () -> numberUtils.isPrime(1),
            "IsPrime should throw IligalArgumentException for 1");

    }

    @Test
    void testIsPrimeWithTwo() {
        NumberUtils numberUtils = new NumberUtils();

        assertTrue(numberUtils.isPrime(2), "isPrime should return true");
    }

    @Test
    void testIsPrimeWithFour() {
        NumberUtils numberUtils = new NumberUtils();

        assertFalse(numberUtils.isPrime(4), "isPrime should return false");
    }

}
