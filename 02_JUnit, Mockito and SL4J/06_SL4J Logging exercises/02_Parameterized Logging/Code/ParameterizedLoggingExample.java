package com.example.parameterized_logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class ParameterizedLoggingExample {
    private static final Logger logger = LoggerFactory.getLogger(ParameterizedLoggingExample.class);

    public static void main(String[] args) {
        String username = "karthiga";
        LocalDateTime loginTime = LocalDateTime.now();

        logger.info("User {} logged in at {}", username, loginTime);
        logger.warn("User {} attempted to access a restricted page at {}", username, LocalDateTime.now());
        logger.error("Login failed for user {} at {}", username, LocalDateTime.now());
    }
}
