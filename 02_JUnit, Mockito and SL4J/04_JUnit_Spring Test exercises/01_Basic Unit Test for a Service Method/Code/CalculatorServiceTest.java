package com.example.test;

import com.example.service.CalculatorService;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorServiceTest {

    private CalculatorService calculatorService;

    @Before
    public void setUp() {
        calculatorService = new CalculatorService();
    }

    @Test
    public void testAdd() {
        int result = calculatorService.add(5, 7);
        assertEquals(12, result);
    }

    @Test
    public void testAddNegative() {
        int result = calculatorService.add(-3, -6);
        assertEquals(-9, result);
    }

    @Test
    public void testAddZero() {
        int result = calculatorService.add(0, 0);
        assertEquals(0, result);
    }
}
