// src/main/java/com/example/countryservice/CountryController.java
package com.example.countryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid; // NEW IMPORT: Required for the @Valid annotation


@RestController // Marks this class as a Spring REST Controller, enabling it to handle web requests.
@RequestMapping("/countries") // Defines the base URI path for all handler methods in this controller.
public class CountryController {

    // Logger instance for logging messages within this class.
    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    /**
     * Handles HTTP POST requests to the "/countries" endpoint.
     *
     * @param country The Country object, which Spring attempts to bind from the
     * JSON request body using the @RequestBody annotation.
     * The @Valid annotation tells Spring to automatically trigger
     * validation on the 'country' object based on the constraints
     * defined in Country.java. If validation fails, Spring will
     * throw a MethodArgumentNotValidException.
     * @return The Country object that was successfully received and validated.
     * Spring will automatically serialize this object back into JSON for the HTTP response.
     */
    @PostMapping() // Maps HTTP POST requests specifically to the /countries path.
    public Country addCountry(@RequestBody @Valid Country country) {
        // Log that the method has been invoked.
        logger.info("Start: addCountry method invoked.");
        // Log the Country object that was successfully deserialized and validated from the request body.
        logger.info("Received Country data: {}", country);

        // All manual validation code has been removed from here.
        // Spring's @Valid annotation, combined with the GlobalExceptionHandler,
        // now handles validation automatically and centrally.

        // In a real application, you would typically save this 'country' object to a database.
        // For this example, we simply return the received 'country' object.
        return country;
    }
}