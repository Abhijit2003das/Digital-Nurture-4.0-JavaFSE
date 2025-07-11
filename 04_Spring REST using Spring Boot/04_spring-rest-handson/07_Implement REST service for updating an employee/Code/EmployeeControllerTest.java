// src/test/java/com/example/countryservice/EmployeeControllerTest.java
package com.example.countryservice;

import com.fasterxml.jackson.databind.ObjectMapper; // For converting Java objects to JSON and vice-versa
import org.junit.jupiter.api.BeforeEach;           // For setup methods before each test
import org.junit.jupiter.api.Test;                 // JUnit 5 annotation for test methods
import org.springframework.beans.factory.annotation.Autowired; // For dependency injection
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc; // To configure MockMvc
import org.springframework.boot.test.context.SpringBootTest;  // For Spring Boot integration tests
import org.springframework.http.MediaType;           // For setting content type in requests
import org.springframework.test.web.servlet.MockMvc; // The main MockMvc object for testing controllers
import org.springframework.test.web.servlet.ResultActions; // To chain assertions

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.slf4j.Logger;         // For logging in tests
import org.slf4j.LoggerFactory;  // For initializing the logger

// Hamcrest Matchers for flexible assertions, especially for list content order
import static org.hamcrest.Matchers.containsInAnyOrder; // NEW IMPORT for the validation error test

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put; // Static import for put() request builder
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath; // For asserting JSON content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status; // For asserting HTTP status
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print; // For printing request/response details


/**
 * Integration tests for EmployeeController using MockMvc.
 * This will test the entire Spring MVC stack without starting a full HTTP server.
 */
@SpringBootTest // Loads the full Spring application context for integration testing.
@AutoConfigureMockMvc // Configures MockMvc for testing the web layer (controllers).
public class EmployeeControllerTest {

    // Logger for this test class, helpful for debugging test execution flow.
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeControllerTest.class);

    @Autowired
    private MockMvc mockMvc; // Injects MockMvc, allowing us to simulate HTTP requests programmatically.

    @Autowired
    private ObjectMapper objectMapper; // Injects ObjectMapper to convert Java objects to JSON strings and vice-versa.

    // Helper for formatting dates to match the "dd/MM/yyyy" pattern used in @JsonFormat.
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    // Sample data to be used across different test methods.
    private Department sampleDepartment;
    private Skill sampleSkill;
    private Employee sampleEmployee;

    /**
     * Setup method that runs before each test method.
     * Initializes common objects like Department, Skill, and a sample Employee.
     */
    @BeforeEach
    public void setup() {
        sampleDepartment = new Department(1, "Human Resources");
        sampleSkill = new Skill(104, "Communication"); // Using an ID from your DAO's initial data.
        try {
            // Initialize a sample employee with valid data for use in tests.
            sampleEmployee = new Employee(
                    1, "John Doe Updated", 85000.0, true,
                    dateFormatter.parse("15/05/1990"), // Parses the date string into a Date object.
                    sampleDepartment,
                    Arrays.asList(sampleSkill) // Using Arrays.asList to create a List of skills.
            );
        } catch (Exception e) {
            // Log any errors during setup, though this should ideally not happen.
            LOGGER.error("Error setting up sample employee: {}", e.getMessage(), e);
        }
    }

    /**
     * Test case for updating an employee with a type mismatch error.
     * Specifically, it tests sending a string value for an integer field (e.g., "id": "invalidId").
     * This scenario is handled by the `handleHttpMessageNotReadable` method in `GlobalExceptionHandler`.
     * @throws Exception if MockMvc operation fails.
     */
    @Test
    public void testUpdateEmployee_TypeMismatchError() throws Exception {
        LOGGER.info("Starting testUpdateEmployee_TypeMismatchError");

        // Manually construct a JSON string with an intentional type mismatch for the 'id' field.
        String invalidEmployeeJson = "{"
                + "\"id\": \"invalidId\"," // This is the intentional type mismatch (string instead of integer)
                + "\"name\": \"Test Name\","
                + "\"salary\": 50000.0,"
                + "\"permanent\": true,"
                + "\"dateOfBirth\": \"01/01/2000\","
                + "\"department\": {\"id\": 1, \"name\": \"HR\"},"
                + "\"skills\": [{\"id\": 101, \"name\": \"Java\"}]"
                + "}";

        // Perform a PUT request to the /employees endpoint.
        ResultActions result = mockMvc.perform(put("/employees")
                .contentType(MediaType.APPLICATION_JSON)      // Set the Content-Type header to application/json.
                .content(invalidEmployeeJson))                // Set the request body to the invalid JSON string.
                .andDo(print());                              // Print the request and response details to the console for debugging.

        // Assertions:
        result.andExpect(status().isBadRequest()) // Expect an HTTP 400 Bad Request status.
                .andExpect(jsonPath("$.status").value(400)) // Assert the 'status' field in the JSON response is 400.
                .andExpect(jsonPath("$.error").value("Bad Request")) // Assert the 'error' field is "Bad Request".
                .andExpect(jsonPath("$.message").exists()) // Assert that a 'message' field exists.
                // Assert the specific error message generated by handleHttpMessageNotReadable.
                .andExpect(jsonPath("$.message").value("Incorrect format for field 'id'. Expected Integer, but received 'invalidId'."))
                // Assert that the 'errors' array contains the specific message.
                .andExpect(jsonPath("$.errors[0]").value("Incorrect format for field 'id'. Expected Integer, but received 'invalidId'."));

        LOGGER.info("Finished testUpdateEmployee_TypeMismatchError");
    }

    /**
     * Test case for updating an employee with a validation error (e.g., name is blank).
     * This scenario is handled by the `handleMethodArgumentNotValid` method in `GlobalExceptionHandler`.
     * @throws Exception if MockMvc operation fails.
     */
    @Test
    public void testUpdateEmployee_ValidationError() throws Exception {
        LOGGER.info("Starting testUpdateEmployee_ValidationError");

        // Create an Employee object with invalid data (empty name).
        Employee invalidEmployee = new Employee(
                1, "", // Invalid: Name cannot be blank and must be at least 1 character.
                85000.0, true,
                dateFormatter.parse("15/05/1990"),
                sampleDepartment,
                Arrays.asList(sampleSkill)
        );

        // Convert the invalid employee object to a JSON string using ObjectMapper.
        String invalidEmployeeJson = objectMapper.writeValueAsString(invalidEmployee);

        // Perform a PUT request.
        ResultActions result = mockMvc.perform(put("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidEmployeeJson))
                .andDo(print());

        // Assertions:
        result.andExpect(status().isBadRequest()) // Expect an HTTP 400 Bad Request status.
                .andExpect(jsonPath("$.status").value(400)) // Assert the 'status' field is 400.
                // Assert that the 'errors' array contains both expected messages, regardless of their order.
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                        "Employee name cannot be blank",
                        "Employee name must be between 1 and 30 characters"
                )));

        LOGGER.info("Finished testUpdateEmployee_ValidationError");
    }

    /**
     * Test case for attempting to update an employee that does not exist in the DAO.
     * This scenario is handled by the `EmployeeNotFoundException` (which has `@ResponseStatus`).
     * @throws Exception if MockMvc operation fails.
     */
    @Test
    public void testUpdateEmployee_NotFound() throws Exception {
        LOGGER.info("Starting testUpdateEmployee_NotFound");

        // Create an Employee object with an ID that is highly unlikely to exist in the initial DAO data.
        Employee nonExistentEmployee = new Employee(
                9999, "Non Existent", 50000.0, true,
                dateFormatter.parse("01/01/2000"),
                sampleDepartment,
                Arrays.asList(sampleSkill)
        );

        // Convert the non-existent employee object to a JSON string.
        String nonExistentEmployeeJson = objectMapper.writeValueAsString(nonExistentEmployee);

        // Perform a PUT request.
        ResultActions result = mockMvc.perform(put("/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(nonExistentEmployeeJson))
                .andDo(print());

        // Assertions:
        result.andExpect(status().isNotFound()) // Expect an HTTP 404 Not Found status.
                .andExpect(jsonPath("$.status").value(404)) // Assert the 'status' field is 404.
                .andExpect(jsonPath("$.error").value("Not Found")) // Assert the 'error' field is "Not Found".
                // Assert the specific error message from EmployeeNotFoundException.
                .andExpect(jsonPath("$.message").value("Employee with ID 9999 not found."));

        LOGGER.info("Finished testUpdateEmployee_NotFound");
    }
}