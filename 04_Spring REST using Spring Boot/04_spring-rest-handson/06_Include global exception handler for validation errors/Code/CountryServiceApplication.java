// src/main/java/com/example/countryservice/CountryServiceApplication.java
package com.example.countryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // This annotation enables auto-configuration and component scanning for your Spring Boot app.
public class CountryServiceApplication {

    public static void main(String[] args) {
        // This is the main method that starts the Spring Boot application.
        SpringApplication.run(CountryServiceApplication.class, args);
    }

}