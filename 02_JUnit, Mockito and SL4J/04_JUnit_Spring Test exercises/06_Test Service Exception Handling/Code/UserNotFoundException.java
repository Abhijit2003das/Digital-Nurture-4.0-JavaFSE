package com.example.usertest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception to be thrown when a User is not found.
 * The @ResponseStatus annotation tells Spring to return HTTP 404 NOT FOUND
 * when this exception is thrown from a controller method.
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // This ensures a 404 status when thrown from a controller
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
