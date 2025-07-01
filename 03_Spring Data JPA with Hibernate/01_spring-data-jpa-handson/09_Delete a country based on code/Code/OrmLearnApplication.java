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

        deleteCountryTest(); // ✅ Run test here
    }

    private static void deleteCountryTest() {
        LOGGER.info("Start");

        String codeToDelete = "XX"; // Code of test country added earlier
        countryService.deleteCountry(codeToDelete);
        LOGGER.debug("Country with code '{}' deleted.", codeToDelete);

        try {
            countryService.findCountryByCode(codeToDelete);
        } catch (CountryNotFoundException e) {
            LOGGER.debug("Country '{}' not found as expected after deletion.", codeToDelete);
        }

        LOGGER.info("End");
    }
}
