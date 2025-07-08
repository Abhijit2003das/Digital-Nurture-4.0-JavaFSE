package com.cognizant.spring_learn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List; // Import List

public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        LOGGER.info("START - main() method");
        // displayCountry(); // You can comment this out or keep it for previous demo
        displayCountries(); // Call the new method
        LOGGER.info("END - main() method");
    }

    // Previous displayCountry method (optional, keep or remove as needed)
    public static void displayCountry() {
        LOGGER.info("START - displayCountry() method");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        Country country1 = context.getBean("country", Country.class);
        Country country2 = context.getBean("country", Country.class);
        LOGGER.debug("Country Object 1: {}", country1);
        LOGGER.debug("Country Object 2: {}", country2);
        LOGGER.debug("Same instance? {}", country1 == country2);
        LOGGER.info("END - displayCountry() method");
    }

    // New method to display list of countries
    public static void displayCountries() {
        LOGGER.info("START - displayCountries() method");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");

        // Retrieve the countryList bean
        // Spring automatically knows that countryList is an ArrayList of Country objects
        List<Country> countries = (List<Country>) context.getBean("countryList");

        // Log the size of the list
        LOGGER.debug("Countries List Size: {}", countries.size());

        // Iterate and log each country
        LOGGER.debug("Countries in the list:");
        for (Country country : countries) {
            LOGGER.debug("  {}", country);
        }

        LOGGER.info("END - displayCountries() method");
    }
}