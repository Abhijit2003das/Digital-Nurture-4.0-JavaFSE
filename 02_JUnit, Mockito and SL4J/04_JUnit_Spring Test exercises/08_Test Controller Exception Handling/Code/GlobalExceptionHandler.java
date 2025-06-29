package com.example.usertest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global Exception Handler for REST controllers.
 * @ControllerAdvice makes this class capable of handling exceptions
 * from any controller across the application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles UserNotFoundException, which is thrown by UserService when a user is not found.
     * This method will return an HTTP 404 Not Found status with a custom message.
     *
     * @param ex The UserNotFoundException that was thrown.
     * @return A ResponseEntity with HTTP 404 status and a custom body.
     */
    @ExceptionHandler(UserNotFoundException.class) // Specifies which exception this method handles
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        // Return HTTP 404 Not Found status with the exception's message as the response body
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    // You can add more @ExceptionHandler methods here for other exception types (e.g., general Exception.class)
    // to provide a fallback for unhandled exceptions.
    // Example:
    /*
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred.");
    }
    */
}
