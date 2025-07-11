package com.example.countryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marks this class as a REST controller
@RequestMapping("/countries") // Base path for all endpoints in this controller
public class CountryController {

    // Logger for this class
    private static final Logger logger = LoggerFactory.getLogger(CountryController.class);

    /**
     * Handles POST requests to /countries.
     * This method is designed to simulate the creation of a new country.
     * For simplicity, it currently only logs a message.
     * In a real application, it would typically accept a Country object in the request body
     * and persist it to a database.
     */
    @PostMapping() // Maps POST requests to /countries
    public void addCountry() {
        logger.info("Start: addCountry method invoked.");
        // In a real application, you would add logic here to:
        // 1. Receive country data from the request body (e.g., @RequestBody Country country)
        // 2. Validate the data
        // 3. Save the country to a database
        // 4. Return an appropriate response (e.g., ResponseEntity<Country> with 201 Created status)
    }
}