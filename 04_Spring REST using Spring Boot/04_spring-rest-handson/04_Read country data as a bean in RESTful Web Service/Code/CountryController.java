// src/main/java/com/example/countryservice/CountryController.java
package com.example.countryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marks this class as a REST controller, making it capable of handling HTTP requests
@RequestMapping("/countries") // Defines the base path for all endpoints in this controller (e.g., /countries)
public class CountryController {

    // Initialize a logger for this class. SLF4J is the logging facade used by Spring Boot.
    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    /**
     * Handles HTTP POST requests to the /countries endpoint.
     *
     * @param country The Country object populated from the JSON request body.
     * The @RequestBody annotation tells Spring to deserialize the incoming
     * HTTP request body into a 'Country' object.
     * @return The Country object that was received, demonstrating successful deserialization
     * and also acting as the response payload. Spring will automatically serialize
     * this Country object back into JSON for the HTTP response.
     */
    @PostMapping() // Maps POST requests specifically to the /countries path (since @RequestMapping is /countries)
    public Country addCountry(@RequestBody Country country) {
        // Log the start of the method execution
        logger.info("Start: addCountry method invoked.");

        // Log the details of the Country object received from the request body.
        // This helps verify that the JSON payload was correctly parsed into the Java bean.
        logger.info("Received Country data: {}", country);

        // In a real-world application, this is where you would typically:
        // 1. Perform validation on the 'country' object (e.g., ensure 'code' is not empty).
        // 2. Call a service layer to persist the 'country' object to a database.
        // 3. Handle any business logic related to adding a new country.
        // 4. Potentially return a ResponseEntity with a 201 Created status and the newly created resource's URI.

        // For this example, we simply return the received 'country' object.
        // Spring will automatically convert this Java object back into JSON
        // and send it as the HTTP response body.
        return country;
    }
}