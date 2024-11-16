package bg.sofia.uni.fmi.mjt.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CalcTest {

    @Test
    void testMultiplyXByZero() {
        Calc calc = new Calc();

        assertEquals(0, calc.multiply(42, 0), "Multiplication with zero should be zero");
    }

    @Test
    void testMultiplyZeroByX() {
        Calc calc = new Calc();

        assertEquals(0, calc.multiply(0, 9), "Multiplication with zero should be zero");
    }

    @Test
    void testMultiplyXByX() {
        Calc calc = new Calc();

        assertNotEquals(0, calc.multiply(2, 3), "Multiplication by zero should return zero.");
    }


}
