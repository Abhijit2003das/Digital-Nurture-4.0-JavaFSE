package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class StringUtilsTest {

    StringUtils utils = new StringUtils();

    @Test
    public void testIsEmpty() {
        assertTrue(utils.isEmpty(""));
        assertTrue(utils.isEmpty(null));
        assertFalse(utils.isEmpty("hello"));
    }

    @Test
    public void testToUpperCase() {
        assertEquals("HELLO", utils.toUpperCase("hello"));
        assertEquals("JAVA", utils.toUpperCase("Java"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToUpperCaseWithNull() {
        utils.toUpperCase(null);
    }

    @Test
    public void testReverse() {
        assertEquals("cba", utils.reverse("abc"));
        assertEquals("4321", utils.reverse("1234"));
        assertNull(utils.reverse(null));
    }
}
