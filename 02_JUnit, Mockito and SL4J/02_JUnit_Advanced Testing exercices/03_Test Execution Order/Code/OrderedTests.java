package com.example.test_order_demo;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(OrderAnnotation.class)
public class OrderedTests {

    @Test
    @Order(1)
    void firstTest() {
        System.out.println("Running first test");
        assertTrue(true);
    }

    @Test
    @Order(3)
    void thirdTest() {
        System.out.println("Running third test");
        assertTrue(true);
    }

    @Test
    @Order(2)
    void secondTest() {
        System.out.println("Running second test");
        assertTrue(true);
    }
}
