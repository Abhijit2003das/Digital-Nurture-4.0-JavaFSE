package com.cognizant.spring_learn;

import java.text.ParseException; // Import for handling date parsing exceptions
import java.text.SimpleDateFormat; // Import for SimpleDateFormat
import java.util.Date; // Import for Date class

import org.slf4j.Logger; // Import for Logger
import org.slf4j.LoggerFactory; // Import for LoggerFactory

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext; // Import for ApplicationContext
import org.springframework.context.support.ClassPathXmlApplicationContext; // Import for ClassPathXmlApplicationContext

@SpringBootApplication
public class SpringLearnApplication {

    // Logger instance for logging messages throughout the application
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringLearnApplication.class);

    /**
     * Demonstrates loading a SimpleDateFormat bean from a Spring XML configuration file,
     * parsing a date string, and displaying the result.
     */
    private static void displayDate() {
        LOGGER.info("START - displayDate() method");

        // Create a Spring ApplicationContext to load beans from the XML configuration file.
        // "date-format.xml" must be present in the src/main/resources folder.
        ApplicationContext context = new ClassPathXmlApplicationContext("date-format.xml");

        // Retrieve the SimpleDateFormat bean by its ID ("dateFormat") and its class type.
        // Spring will provide an instance of SimpleDateFormat configured with "dd/MM/yyyy"
        // as defined in date-format.xml.
        SimpleDateFormat format = context.getBean("dateFormat", SimpleDateFormat.class);

        // Define a date string to be parsed
        String dateString = "31/12/2018";

        try {
            // Use the retrieved SimpleDateFormat instance to parse the string into a Date object.
            Date parsedDate = format.parse(dateString);

            // Display the parsed date to the console.
            System.out.println("Parsed Date: " + parsedDate);

            // Log a debug message confirming successful parsing.
            // The output timezone (e.g., IST) depends on your system's default timezone.
            LOGGER.debug("Successfully parsed '{}' to Date: {}", dateString, parsedDate);

        } catch (ParseException e) {
            // Catch ParseException if the date string does not match the SimpleDateFormat pattern.
            LOGGER.error("Error parsing date '{}': {}", dateString, e.getMessage());
            e.printStackTrace(); // Print the full stack trace for detailed debugging.
        } finally {
            // It's good practice to close the ClassPathXmlApplicationContext
            // when it's used as a standalone context to release resources.
            if (context instanceof ClassPathXmlApplicationContext) {
                ((ClassPathXmlApplicationContext) context).close();
            }
        }

        LOGGER.info("END - displayDate() method");
    }

    /**
     * The main entry point for the Spring Boot application.
     * This method initializes and runs the Spring application context.
     *
     * @param args Command line arguments passed to the application.
     */
    public static void main(String[] args) {
        // Log a message indicating the start of the Spring Boot application.
        LOGGER.info("SpringLearnApplication started successfully!");

        // Run the Spring Boot application. This sets up the Spring context,
        // scans for components, and starts the embedded web server (if applicable).
        SpringApplication.run(SpringLearnApplication.class, args);

        // Call our custom method to demonstrate Spring Core XML configuration.
        // This call happens after the main Spring Boot application context has started.
        displayDate();
    }
}