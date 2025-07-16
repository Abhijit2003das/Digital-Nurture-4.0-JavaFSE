package com.cognizant.greetservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController; // Import this annotation

/**
 * REST Controller for the Greet Service.
 *
 * @RestController: A convenience annotation that combines @Controller and @ResponseBody,
 * making it suitable for building RESTful web services.
 * It indicates that this class will handle incoming web requests
 * and return JSON/XML responses directly.
 */
@RestController
public class GreetController {

    /**
     * Handles GET requests to the "/greet" endpoint.
     *
     * @GetMapping("/greet"): Maps HTTP GET requests to the /greet path to this method.
     * When accessed, it will return the string "Hello World".
     * @return A simple greeting string.
     */
    @GetMapping("/greet")
    public String greet() {
        return "Hello World";
    }
}