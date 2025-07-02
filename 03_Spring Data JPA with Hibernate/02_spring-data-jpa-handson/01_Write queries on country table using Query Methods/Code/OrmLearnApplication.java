package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;

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

		// 👉 Call test methods here
		// getCountryByCodeTest();
		// addCountryTest();
		// updateCountryTest();
		// deleteCountryTest();
		// searchCountriesBySubstringTest();
		// searchCountriesOrderedTest();
		// searchCountriesByPrefixTest();

		// 👇 Example
		searchCountriesBySubstringTest();
	}

	private static void searchCountriesBySubstringTest() {
		LOGGER.info("Start");
		List<Country> countries = countryService.getCountriesContaining("ou");
		for (Country country : countries) {
			LOGGER.debug("Country: {}", country);
		}
		LOGGER.info("End");
	}
}
