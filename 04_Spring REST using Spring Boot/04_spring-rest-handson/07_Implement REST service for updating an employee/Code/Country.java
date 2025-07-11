// src/main/java/com/example/countryservice/Country.java
package com.example.countryservice;

import jakarta.validation.constraints.NotNull; // Import for @NotNull annotation
import jakarta.validation.constraints.Size;   // Import for @Size annotation

public class Country {

    // @NotNull ensures that the 'code' field must not be null in the incoming JSON payload.
    // @Size ensures that the 'code' field must have a length of exactly 2 characters.
    // The 'message' attribute provides a custom error message if the validation constraint is violated.
    @NotNull(message = "Country code cannot be null")
    @Size(min = 2, max = 2, message = "Country code should be exactly 2 characters")
    private String code;
    private String name;

    // Default constructor: Essential for Spring/Jackson to deserialize JSON into a Country object.
    // Jackson uses this to create an instance of the object before populating its fields.
    public Country() {
    }

    // Parameterized constructor: Optional, but useful for creating Country objects programmatically (e.g., in tests).
    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Public getter for 'code': Used by Jackson when serializing the Country object to JSON for responses.
    public String getCode() {
        return code;
    }

    // Public setter for 'code': Used by Jackson when deserializing JSON into the Country object.
    public void setCode(String code) {
        this.code = code;
    }

    // Public getter for 'name'.
    public String getName() {
        return name;
    }

    // Public setter for 'name'.
    public void setName(String name) {
        this.name = name;
    }

    // Overriding toString() method: Provides a human-readable string representation of the object.
    // This is very helpful for logging and debugging purposes.
    @Override
    public String toString() {
        return "Country{" +
               "code='" + code + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}