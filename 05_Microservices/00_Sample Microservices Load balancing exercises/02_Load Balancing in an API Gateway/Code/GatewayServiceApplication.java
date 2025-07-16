package com.example.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot application class for the API Gateway.
 * This class serves as the entry point for starting the Spring Cloud Gateway application.
 *
 * {@code @SpringBootApplication} is a convenience annotation that combines:
 * - {@code @Configuration}: Tags the class as a source of bean definitions for the application context.
 * - {@code @EnableAutoConfiguration}: Tells Spring Boot to start adding beans based on classpath settings,
 * other beans, and various property settings. For example, if Spring Cloud Gateway and
 * Spring Cloud LoadBalancer are on the classpath, they will be auto-configured.
 * - {@code @ComponentScan}: Tells Spring to look for other components, configurations, and services
 * in the `com.example.gatewayservice` package (and its sub-packages), allowing it to discover
 * beans like our `LoadBalancerConfiguration` and any custom filters.
 */
@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		// This is the standard main method that launches the Spring Boot application.
		// It creates an appropriate ApplicationContext instance and loads all defined beans.
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

}
