package com.cognizant.spring_learn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    public static void main(String[] args) {
        LOGGER.info("START - main() method");
        displayCountry();
        LOGGER.info("END - main() method");
    }

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

}
