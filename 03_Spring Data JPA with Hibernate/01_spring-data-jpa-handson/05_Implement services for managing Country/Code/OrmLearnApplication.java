package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);
    private static CountryService countryService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        countryService = context.getBean(CountryService.class);
        LOGGER.info("Inside main");

        try {
            testFindCountryByCode();
            testAddCountry();
            testUpdateCountry();
            testDeleteCountry();
            testFindCountriesByNameContaining();
        } catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }
    }

    private static void testFindCountryByCode() throws Exception {
        LOGGER.info("Start");
        Country country = countryService.findCountryByCode("IN");
        LOGGER.debug("Country: {}", country);
        LOGGER.info("End");
    }

    private static void testAddCountry() {
        LOGGER.info("Start");
        Country country = new Country();
        country.setCode("ZZ");
        country.setName("Testland");
        countryService.addCountry(country);
        LOGGER.info("Country added");
        LOGGER.info("End");
    }

    private static void testUpdateCountry() throws Exception {
        LOGGER.info("Start");
        countryService.updateCountry("ZZ", "Testlandia");
        LOGGER.info("Country updated");
        LOGGER.info("End");
    }

    private static void testDeleteCountry() {
        LOGGER.info("Start");
        countryService.deleteCountry("ZZ");
        LOGGER.info("Country deleted");
        LOGGER.info("End");
    }

    private static void testFindCountriesByNameContaining() {
        LOGGER.info("Start");
        List<Country> countries = countryService.findCountriesByNameContaining("land");
        LOGGER.debug("Countries: {}", countries);
        LOGGER.info("End");
    }
}
