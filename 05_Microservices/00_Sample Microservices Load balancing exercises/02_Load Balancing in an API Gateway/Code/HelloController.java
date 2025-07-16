package com.example.backendservice2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Backend Service Instance 2.
 * This controller provides an endpoint that returns a message specific to this instance.
 */
@RestController
public class HelloController {

    /**
     * Handles GET requests to /hello and returns a greeting from instance 2.
     * @return A string message indicating the source of the response.
     */
    @GetMapping("/hello")
    public String hello() {
        System.out.println("Request received by Backend Service Instance 2 (Port 8082)");
        return "Hello from Backend Service Instance 2 (Port 8082)!";
    }
}
