// src/main/java/com/example/countryservice/Country.java
package com.example.countryservice;

public class Country {
    private String code;
    private String name;

    // Default constructor is essential for Spring/Jackson to deserialize JSON
    public Country() {
    }

    // Parameterized constructor (optional, but good for convenience)
    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Getters
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // Setters
    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Override toString() for easy logging and debugging
    @Override
    public String toString() {
        return "Country{" +
               "code='" + code + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}