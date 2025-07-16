package com.example.edgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for the Edge Service.
 * This class serves as the entry point for starting the Spring Cloud Gateway application.
 *
 * {@code @SpringBootApplication} is a convenience annotation that adds:
 * - {@code @Configuration}: Tags the class as a source of bean definitions for the application context.
 * - {@code @EnableAutoConfiguration}: Tells Spring Boot to start adding beans based on classpath settings,
 * other beans, and various property settings. For example, if Spring Cloud Gateway is on the classpath,
 * it will auto-configure the Gateway.
 * - {@code @ComponentScan}: Tells Spring to look for other components, configurations, and services
 * in the `com.example.edgeservice` package, allowing it to discover our `LoggingFilter`.
 */
@SpringBootApplication
public class EdgeServiceApplication {

	public static void main(String[] args) {
		// This is the standard main method that launches the Spring Boot application.
		// It creates an appropriate ApplicationContext instance and loads beans.
		SpringApplication.run(EdgeServiceApplication.class, args);
	}

}
