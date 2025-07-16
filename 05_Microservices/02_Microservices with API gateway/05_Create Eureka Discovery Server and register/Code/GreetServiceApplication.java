package com.cognizant.greetservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient; // Import this annotation

/**
 * Main application class for the Greet Service.
 *
 * @SpringBootApplication: This is a convenience annotation that adds:
 * - @Configuration: Tags the class as a source of bean definitions.
 * - @EnableAutoConfiguration: Tells Spring Boot to start adding beans
 * based on classpath settings, other beans, and various property settings.
 * - @ComponentScan: Tells Spring to look for other components, configurations,
 * and services in the 'com.cognizant.greetservice' package, allowing it
 * to find our controllers and other components.
 *
 * @EnableDiscoveryClient: This annotation enables the service to register itself with
 * a Discovery Server (like Eureka) and to discover other services.
 */
@SpringBootApplication
@EnableDiscoveryClient // Enables this service to be a Eureka client
public class GreetServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreetServiceApplication.class, args);
	}

}