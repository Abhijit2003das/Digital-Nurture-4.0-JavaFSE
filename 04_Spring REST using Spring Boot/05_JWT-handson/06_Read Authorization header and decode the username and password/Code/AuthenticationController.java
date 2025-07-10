package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64; // Import for Base64 decoding
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @GetMapping("/authenticate")
    public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
        LOGGER.info("Start authenticate()");
        LOGGER.debug("Authorization Header: {}", authHeader); // Debug log for the full header

        // Invoke the getUser() method to decode and extract the username
        String username = getUser(authHeader);
        LOGGER.debug("Decoded Username: {}", username); // Log the extracted username

        Map<String, String> response = new HashMap<>();
        response.put("token", ""); // Return an empty token string as requested

        LOGGER.info("End authenticate()");
        return response;
    }

    /**
     * Private method to decode the Authorization header and extract the username.
     * Assumes Basic authentication format: "Basic <Base64-encoded-username:password>"
     *
     * @param authHeader The full Authorization header string.
     * @return The decoded username.
     */
    private String getUser(String authHeader) {
        LOGGER.debug("Start getUser() with authHeader: {}", authHeader);

        // 1. Get the Base64 encoded text after "Basic "
        // Example: "Basic dXNlcjpwd2Q=" -> "dXNlcjpwd2Q="
        String encodedCredentials = authHeader.substring("Basic ".length());
        LOGGER.debug("Encoded Credentials: {}", encodedCredentials);

        // 2. Decode it using Base64.getDecoder().decode()
        // This returns a byte array
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
        // LOGGER.debug("Decoded Bytes (raw): {}", decodedBytes); // Can be noisy, typically not logged directly

        // 3. Convert the byte array to a String (e.g., "user:pwd")
        String decodedCredentials = new String(decodedBytes);
        LOGGER.debug("Decoded Credentials String (username:password): {}", decodedCredentials);

        // 4. Get the text until colon to get the username
        // Example: "user:pwd" -> "user"
        String username = decodedCredentials.substring(0, decodedCredentials.indexOf(":"));
        LOGGER.debug("Extracted Username: {}", username);

        LOGGER.debug("End getUser()");
        return username;
    }
}