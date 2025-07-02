package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.model.Stock;
import com.cognizant.ormlearn.repository.StockRepository;
import com.cognizant.ormlearn.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    @Autowired
    private CountryService countryService;

    @Autowired
    private StockRepository stockRepository;

    public static void main(String[] args) {
        SpringApplication.run(OrmLearnApplication.class, args);
    }

    @PostConstruct
    public void run() {
        // Uncomment the methods you want to test
        // testGetAllCountries();
        // testAddCountry();
        // testUpdateCountry();
        // testDeleteCountry();
        // searchCountriesBySubstringTest();
        // searchCountriesBySubstringSortedTest();
        // searchCountriesByStartingAlphabetTest();

        stockFacebookSept2019();
        stockGoogleCloseGreaterThan1250();
        top3HighVolumeStocks();
        netflixLowest3Stocks();
    }

    // --- Country Test Methods ---

    public void testGetAllCountries() {
        LOGGER.info("Start testGetAllCountries");
        List<Country> list = countryService.getAllCountries();
        list.forEach(c -> LOGGER.debug("Country: {}", c));
        LOGGER.info("End testGetAllCountries");
    }

    public void testAddCountry() {
        LOGGER.info("Start testAddCountry");
        Country country = new Country();
        country.setCode("XY");
        country.setName("Xyland");
        countryService.addCountry(country);
        LOGGER.debug("Added Country: {}", countryService.findCountryByCode("XY"));
        LOGGER.info("End testAddCountry");
    }

    public void testUpdateCountry() {
        LOGGER.info("Start testUpdateCountry");
        countryService.updateCountry("XY", "Updated Xyland");
        Country updated = countryService.findCountryByCode("XY");
        LOGGER.debug("Updated Country: {}", updated);
        LOGGER.info("End testUpdateCountry");
    }

    public void testDeleteCountry() {
        LOGGER.info("Start testDeleteCountry");
        countryService.deleteCountry("XY");
        try {
            countryService.findCountryByCode("XY");
        } catch (Exception e) {
            LOGGER.debug("Country deleted, not found");
        }
        LOGGER.info("End testDeleteCountry");
    }

    public void searchCountriesBySubstringTest() {
        LOGGER.info("Start searchCountriesBySubstringTest");
        List<Country> list = countryService.searchByNameContaining("ou");
        list.forEach(c -> LOGGER.debug("Country: {}", c));
        LOGGER.info("End searchCountriesBySubstringTest");
    }

    public void searchCountriesBySubstringSortedTest() {
        LOGGER.info("Start searchCountriesBySubstringSortedTest");
        List<Country> list = countryService.searchByNameContainingOrderByNameAsc("ou");
        list.forEach(c -> LOGGER.debug("Country: {}", c));
        LOGGER.info("End searchCountriesBySubstringSortedTest");
    }

    public void searchCountriesByStartingAlphabetTest() {
        LOGGER.info("Start searchCountriesByStartingAlphabetTest");
        List<Country> list = countryService.searchByNameStartingWith("Z");
        list.forEach(c -> LOGGER.debug("Country: {}", c));
        LOGGER.info("End searchCountriesByStartingAlphabetTest");
    }

    // --- Stock Query Methods ---

    public void stockFacebookSept2019() {
        LOGGER.info("Start stockFacebookSept2019");
        LocalDate start = LocalDate.of(2019, 9, 1);
        LocalDate end = LocalDate.of(2019, 9, 30);
        List<Stock> list = stockRepository.findByCodeAndDateBetween("FB", start, end);
        list.forEach(s -> LOGGER.debug("Stock: {}", s));
        LOGGER.info("End stockFacebookSept2019");
    }

    public void stockGoogleCloseGreaterThan1250() {
        LOGGER.info("Start stockGoogleCloseGreaterThan1250");
        List<Stock> list = stockRepository.findByCodeAndCloseGreaterThan("GOOGL", new BigDecimal("1250"));
        list.forEach(s -> LOGGER.debug("Stock: {}", s));
        LOGGER.info("End stockGoogleCloseGreaterThan1250");
    }

    public void top3HighVolumeStocks() {
        LOGGER.info("Start top3HighVolumeStocks");
        List<Stock> list = stockRepository.findTop3ByOrderByVolumeDesc();
        list.forEach(s -> LOGGER.debug("Stock: {}", s));
        LOGGER.info("End top3HighVolumeStocks");
    }

    public void netflixLowest3Stocks() {
        LOGGER.info("Start netflixLowest3Stocks");
        List<Stock> list = stockRepository.findTop3ByCodeOrderByCloseAsc("NFLX");
        list.forEach(s -> LOGGER.debug("Stock: {}", s));
        LOGGER.info("End netflixLowest3Stocks");
    }
}
