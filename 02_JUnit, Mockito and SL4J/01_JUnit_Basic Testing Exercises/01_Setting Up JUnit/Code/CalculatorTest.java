package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorTest {

    Calculator calc = new Calculator();

    @Test
    public void testAddition() {
        assertEquals(10, calc.add(7, 3));
    }

    @Test
    public void testSubtraction() {
        assertEquals(4, calc.subtract(10, 6));
    }

    @Test
    public void testMultiplication() {
        assertEquals(20, calc.multiply(4, 5));
    }

    @Test
    public void testDivision() {
        assertEquals(2, calc.divide(10, 5));
    }

    @Test
    public void testDivisionByZero() {
        try {
            calc.divide(10, 0);
            fail("Expected ArithmeticException not thrown");
        } catch (ArithmeticException ex) {
            assertEquals("Cannot divide by zero", ex.getMessage());
        }
    }
}
