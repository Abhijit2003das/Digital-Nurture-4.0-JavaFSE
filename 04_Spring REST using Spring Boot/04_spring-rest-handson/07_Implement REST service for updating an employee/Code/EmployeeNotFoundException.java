// src/main/java/com/example/countryservice/EmployeeNotFoundException.java
package com.example.countryservice;

import org.springframework.http.HttpStatus;           // For specifying the HTTP status code
import org.springframework.web.bind.annotation.ResponseStatus; // Annotation to map exception to HTTP status

/**
 * Custom exception to indicate that an Employee was not found.
 * Annotated with @ResponseStatus to automatically return an HTTP 404 Not Found
 * when this exception is thrown from a Spring controller.
 */
@ResponseStatus(HttpStatus.NOT_FOUND) // Maps this exception to an HTTP 404 Not Found status.
public class EmployeeNotFoundException extends RuntimeException {

    // Default constructor
    public EmployeeNotFoundException() {
        super();
    }

    // Constructor with a custom message
    public EmployeeNotFoundException(String message) {
        super(message);
    }

    // Constructor with a custom message and a cause
    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor with a cause
    public EmployeeNotFoundException(Throwable cause) {
        super(cause);
    }

    // You can add more specific constructors or fields if needed,
    // for example, to include the ID of the employee that was not found.
    public EmployeeNotFoundException(Integer employeeId) {
        super("Employee with ID " + employeeId + " not found.");
    }
}