package com.example.jwtauthdemo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marks this class as a REST controller.
@RequestMapping("/api") // Base path for secure API endpoints.
public class SecureController {

    /**
     * A secure endpoint that requires a valid JWT for access.
     * Accessing this endpoint without a valid JWT will result in a 401 Unauthorized response.
     *
     * @return A string message indicating successful access and the authenticated username.
     */
    @GetMapping("/secure")
    public String secureEndpoint() {
        // Retrieve the authenticated user's information from the SecurityContextHolder.
        // This information is populated by the JwtTokenFilter after successful JWT validation.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username of the authenticated principal.

        return "Hello, " + username + "! This is a secure endpoint accessible with a valid JWT.";
    }
}
