// src/main/java/com/example/countryservice/Country.java
package com.example.countryservice;

import jakarta.validation.constraints.NotNull; // Import for @NotNull
import jakarta.validation.constraints.Size;   // Import for @Size

public class Country {

    // @NotNull ensures that the 'code' field must not be null in the incoming JSON.
    // @Size ensures that the 'code' field must have a length of exactly 2 characters.
    // The 'message' attribute provides a custom error message if the validation fails.
    @NotNull(message = "Country code cannot be null")
    @Size(min = 2, max = 2, message = "Country code should be exactly 2 characters")
    private String code;
    private String name;

    // Default constructor is essential for Spring/Jackson to deserialize JSON.
    // Jackson uses this to create an instance of the object before populating its fields.
    public Country() {
    }

    // Parameterized constructor (optional, but good for convenience in testing or manual creation).
    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // Public getter for 'code'. Used by Jackson when serializing the object to JSON.
    public String getCode() {
        return code;
    }

    // Public setter for 'code'. Used by Jackson when deserializing JSON into the object.
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

    // Overriding toString() is good practice for debugging and logging,
    // as it provides a human-readable representation of the object.
    @Override
    public String toString() {
        return "Country{" +
               "code='" + code + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}