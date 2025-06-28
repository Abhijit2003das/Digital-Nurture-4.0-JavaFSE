package com.example.test_suite_demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorSubtractTest {
    Calculator calc = new Calculator();

    @Test
    public void testSubtraction() {
        assertEquals(1, calc.subtract(3, 2));
    }
}
