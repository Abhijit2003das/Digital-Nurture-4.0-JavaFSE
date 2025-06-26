package com.example;

public class StringUtils {

    // Returns true if the string is empty or null
    public boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    // Returns the string in uppercase
    public String toUpperCase(String str) {
        if (str == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return str.toUpperCase();
    }

    // Reverses the given string
    public String reverse(String str) {
        if (str == null) return null;
        return new StringBuilder(str).reverse().toString();
    }
}
