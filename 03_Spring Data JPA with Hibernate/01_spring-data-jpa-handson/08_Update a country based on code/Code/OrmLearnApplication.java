package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class OrmLearnApplication {

    private static CountryService countryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    public static void main(String[] args) throws CountryNotFoundException {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        countryService = context.getBean(CountryService.class);

        updateCountryTest();  // Run update test
    }

    private static void updateCountryTest() throws CountryNotFoundException {
        LOGGER.info("Start");

        // Update country name
        countryService.updateCountry("IN", "Bharat");

        // Fetch and verify update
        Country updated = countryService.findCountryByCode("IN");
        LOGGER.debug("Updated Country: {}", updated);

        LOGGER.info("End");
    }
}
