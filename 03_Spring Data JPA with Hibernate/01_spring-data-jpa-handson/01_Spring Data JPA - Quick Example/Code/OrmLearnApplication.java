package com.cognizant.ormlearn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext; // Import ApplicationContext

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService; // Import CountryService

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    // Declare a static reference to CountryService
    private static CountryService countryService;

    public static void main(String[] args) {
        // Get the Spring application context
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);

        // Get the CountryService bean from the context
        countryService = context.getBean(CountryService.class);

        LOGGER.info("Inside main");

        // Call the test method
        testGetAllCountries();
    }

    // Define the test method
    private static void testGetAllCountries() {
        LOGGER.info("Start testGetAllCountries");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("Countries: {}", countries); // Use {} for better logging of objects
        LOGGER.info("End testGetAllCountries");
    }
}