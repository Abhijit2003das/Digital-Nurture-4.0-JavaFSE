// src/main/java/com/example/countryservice/CountryController.java
package com.example.countryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus; // Required for HttpStatus enum (e.g., BAD_REQUEST)
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException; // Required for throwing HTTP status exceptions

// Imports for Jakarta Bean Validation API
import jakarta.validation.Validation;          // Provides access to ValidatorFactory
import jakarta.validation.Validator;           // Interface for performing validation
import jakarta.validation.ValidatorFactory;    // Factory for creating Validator instances
import jakarta.validation.ConstraintViolation; // Represents a single validation error

// Standard Java utility imports
import java.util.ArrayList;                     // For creating a mutable list of errors
import java.util.List;                          // Interface for list
import java.util.Set;                           // For collecting unique constraint violations


@RestController // Marks this class as a Spring REST Controller, enabling it to handle web requests.
@RequestMapping("/countries") // Defines the base URI path for all handler methods in this controller.
public class CountryController {

    // Logger instance for logging messages within this class.
    // SLF4J (Simple Logging Facade for Java) is used, which Spring Boot integrates with Logback by default.
    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    /**
     * Handles HTTP POST requests to the "/countries" endpoint.
     * This method is responsible for receiving country data, validating it,
     * and returning the received country or an error if validation fails.
     *
     * @param country The Country object, which Spring attempts to bind from the
     * JSON request body using the @RequestBody annotation.
     * @return The validated Country object (if no errors), which Spring will
     * automatically serialize back into JSON for the HTTP response.
     * @throws ResponseStatusException if validation fails, indicating a 400 Bad Request.
     */
    @PostMapping() // Maps HTTP POST requests to the /countries path (relative to @RequestMapping("/countries")).
    public Country addCountry(@RequestBody Country country) {
        // Log that the method has been invoked.
        logger.info("Start: addCountry method invoked.");
        // Log the Country object that was successfully deserialized from the request body.
        logger.info("Received Country data: {}", country);

        // --- Manual Bean Validation Logic ---
        // 1. Create a ValidatorFactory: This is an expensive operation, typically done once
        //    and reused, but for this hands-on, it's shown inline.
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        // 2. Get a Validator instance from the factory.
        Validator validator = factory.getValidator();

        // 3. Perform validation: The 'validate' method returns a Set of ConstraintViolation objects.
        //    Each ConstraintViolation represents a single validation error.
        Set<ConstraintViolation<Country>> violations = validator.validate(country);

        // 4. Prepare a list to accumulate error messages.
        List<String> errors = new ArrayList<>();

        // 5. Iterate through the violations and add their messages to the errors list.
        for (ConstraintViolation<Country> violation : violations) {
            errors.add(violation.getMessage());
        }

        // 6. Check if any violations were found.
        if (violations.size() > 0) {
            // If there are violations, log them as a warning.
            logger.warn("Validation errors: {}", errors);
            // Throw a ResponseStatusException, which Spring will catch and convert
            // into an HTTP 400 Bad Request response. The error messages are included
            // in the response body.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.toString());
        }
        // --- End of Manual Bean Validation Logic ---

        // If validation passes, return the Country object.
        // Spring will automatically serialize this object into a JSON response.
        return country;
    }
}