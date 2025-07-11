// src/test/java/com/example/countryservice/EmployeeControllerTest.java
package com.example.countryservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List; // Required for List.of() and type hints

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Hamcrest Matchers for flexible assertions, especially for list content order
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasItem;   // Required for 'hasItem' matcher
import static org.hamcrest.Matchers.not;       // Required for 'not' matcher

// Spring MockMvc RequestBuilders and ResultMatchers
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


/**
 * Integration tests for EmployeeController using MockMvc.
 * This class loads the full Spring application context and simulates HTTP requests
 * to test the entire web layer, including controllers, services, and DAOs.
 */
@SpringBootTest // Loads the full Spring application context for integration testing.
@AutoConfigureMockMvc // Configures and injects MockMvc for testing web layer components.
public class EmployeeControllerTest {

    // Logger for this test class, used to log test execution flow and debug information.
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeControllerTest.class);

    @Autowired
    private MockMvc mockMvc; // Injected MockMvc instance for performing simulated HTTP requests.

    @Autowired
    private ObjectMapper objectMapper; // Injected ObjectMapper for converting Java objects to/from JSON.

    // Autowire EmployeeDao to allow resetting its static in-memory data before each test.
    @Autowired
    private EmployeeDao employeeDao;

    // SimpleDateFormat for consistent date formatting, matching the @JsonFormat pattern in Employee.java.
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");

    // Sample data objects to be used across different test methods.
    private Department sampleDepartment;
    private Skill sampleSkill;

    /**
     * Helper method to reset the static in-memory data in EmployeeDao.
     * This is crucial for test isolation, ensuring that each test starts with a clean and consistent data set,
     * regardless of the order in which tests are executed or modifications made by previous tests.
     * @throws Exception if date parsing fails (unlikely in a controlled test setup).
     */
    private void resetEmployeeDaoData() throws Exception {
        LOGGER.info("Resetting EmployeeDao data for test setup.");

        // Clear all static lists in EmployeeDao to ensure a clean slate.
        employeeDao.getEMPLOYEE_LIST().clear();
        employeeDao.getDEPARTMENT_LIST().clear();
        employeeDao.getSKILL_LIST().clear();

        // Re-populate the static lists with the initial dummy data.
        Department hrDept = new Department(1, "Human Resources");
        Department itDept = new Department(2, "Information Technology");
        Department salesDept = new Department(3, "Sales");
        employeeDao.getDEPARTMENT_LIST().add(hrDept);
        employeeDao.getDEPARTMENT_LIST().add(itDept);
        employeeDao.getDEPARTMENT_LIST().add(salesDept);

        Skill javaSkill = new Skill(101, "Java");
        Skill springSkill = new Skill(102, "Spring Boot");
        Skill sqlSkill = new Skill(103, "SQL");
        Skill communicationSkill = new Skill(104, "Communication");
        Skill marketingSkill = new Skill(105, "Digital Marketing");
        employeeDao.getSKILL_LIST().add(javaSkill);
        employeeDao.getSKILL_LIST().add(springSkill);
        employeeDao.getSKILL_LIST().add(sqlSkill);
        employeeDao.getSKILL_LIST().add(communicationSkill);
        employeeDao.getSKILL_LIST().add(marketingSkill);

        employeeDao.getEMPLOYEE_LIST().add(new Employee(
            1, "John Doe", 75000.00, true, dateFormatter.parse("15/05/1990"),
            itDept, List.of(javaSkill, springSkill, sqlSkill)
        ));
        employeeDao.getEMPLOYEE_LIST().add(new Employee(
            2, "Jane Smith", 82000.50, true, dateFormatter.parse("20/08/1992"),
            salesDept, List.of(communicationSkill, marketingSkill)
        ));
        employeeDao.getEMPLOYEE_LIST().add(new Employee(
            3, "Alice Johnson", 60000.00, false, dateFormatter.parse("10/02/1995"),
            hrDept, List.of(communicationSkill, sqlSkill)
        ));
        LOGGER.info("EmployeeDao data successfully reset for the current test run.");
    }

    /**
     * Setup method that runs before each test method.
     * It calls `resetEmployeeDaoData()` to ensure a clean state for every test.
     * @throws Exception if the reset operation fails.
     */
    @BeforeEach
    public void setup() throws Exception {
        resetEmployeeDaoData(); // Ensure data is reset before each test.

        // Initialize sample department and skill for use in tests.
        // These are re-created to ensure they are consistent with the reset DAO data.
        sampleDepartment = new Department(1, "Human Resources");
        sampleSkill = new Skill(104, "Communication");
    }

    /**
     * Test case for updating an employee with a type mismatch error.
     * This specifically tests the `handleHttpMessageNotReadable` method in `GlobalExceptionHandler`.
     * It simulates sending a string value for an integer field (e.g., "id": "invalidId").
     * @throws Exception if MockMvc operation fails.
     */
    @Test
    public void testUpdateEmployee_TypeMismatchError() throws Exception {
        LOGGER.info("Starting testUpdateEmployee_TypeMismatchError");

        // Manually construct a JSON string with an intentional type mismatch for the 'id' field.
        String invalidEmployeeJson = "{"
                + "\"id\": \"invalidId\"," // Intentional type mismatch: string instead of integer.
                + "\"name\": \"Test Name\","
                + "\"salary\": 50000.0,"
                + "\"permanent\": true,"
                + "\"dateOfBirth\": \"01/01/2000\","
                + "\"department\": {\"id\": 1, \"name\": \"HR\"},"
                + "\"skills\": [{\"id\": 101, \"name\": \"Java\"}]"
                + "}";

        // Perform a PUT request to the /employees endpoint.
        ResultActions result = mockMvc.perform(put("/employees")
                .contentType(MediaType.APPLICATION_JSON)      // Set Content-Type header.
                .content(invalidEmployeeJson))                // Set the request body.
                .andDo(print());                              // Print request/response details for debugging.

        // Assertions for the expected HTTP status and JSON response body.
        result.andExpect(status().isBadRequest()) // Expect HTTP 400 Bad Request.
                .andExpect(jsonPath("$.status").value(400)) // Assert 'status' field in JSON response.
                .andExpect(jsonPath("$.error").value("Bad Request")) // Assert 'error' field.
                .andExpect(jsonPath("$.message").exists()) // Ensure 'message' field exists.
                .andExpect(jsonPath("$.message").value("Incorrect format for field 'id'. Expected Integer, but received 'invalidId'."))
                .andExpect(jsonPath("$.errors[0]").value("Incorrect format for field 'id'. Expected Integer, but received 'invalidId'."));

        LOGGER.info("Finished testUpdateEmployee_TypeMismatchError");
    }

    /**
     * Test case for updating an employee with a validation error (e.g., name is blank).
     * This tests the `handleMethodArgumentNotValid` method in `GlobalExceptionHandler`,
     * which is triggered by the `@Valid` annotation on the controller method parameter.
     * @throws Exception if MockMvc operation fails.
     */
    @Test
    public void testUpdateEmployee_ValidationError() throws Exception {
        LOGGER.info("Starting testUpdateEmployee_ValidationError");

        // Create an Employee object with invalid data (empty name string).
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
        result.andExpect(status().isBadRequest()) // Expect HTTP 400 Bad Request.
                .andExpect(jsonPath("$.status").value(400)) // Assert 'status' field is 400.
                // Assert that the 'errors' array contains both expected messages, regardless of their order.
                // Using containsInAnyOrder for robustness against non-guaranteed validation message order.
                .andExpect(jsonPath("$.errors", containsInAnyOrder(
                        "Employee name cannot be blank",
                        "Employee name must be between 1 and 30 characters"
                )));

        LOGGER.info("Finished testUpdateEmployee_ValidationError");
    }

    /**
     * Test case for attempting to update an employee that does not exist in the DAO.
     * This scenario is handled by the `EmployeeNotFoundException` (which is caught by `GlobalExceptionHandler`).
     * @throws Exception if MockMvc operation fails.
     */
    @Test
    public void testUpdateEmployee_NotFound() throws Exception {
        LOGGER.info("Starting testUpdateEmployee_NotFound");

        // Create an Employee object with an ID that is highly unlikely to exist in the initial DAO data.
        Integer nonExistentEmployeeId = 9999;
        Employee nonExistentEmployee = new Employee(
                nonExistentEmployeeId, "Non Existent", 50000.0, true,
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
        result.andExpect(status().isNotFound()) // Expect HTTP 404 Not Found.
                .andExpect(jsonPath("$.status").value(404)) // Assert 'status' field is 404.
                .andExpect(jsonPath("$.error").value("Not Found")) // Assert 'error' field is "Not Found".
                // Assert the specific error message from EmployeeNotFoundException, as formatted by GlobalExceptionHandler.
                .andExpect(jsonPath("$.message").value("Employee with ID " + nonExistentEmployeeId + " not found."));

        LOGGER.info("Finished testUpdateEmployee_NotFound");
    }

    /**
     * Test case for successful deletion of an employee.
     * This also verifies the remaining count and absence of the deleted employee
     * by making a subsequent GET /employees request.
     * @throws Exception if MockMvc operation fails.
     */
    @Test
    public void testDeleteEmployee_Success() throws Exception {
        LOGGER.info("Starting testDeleteEmployee_Success");

        Integer employeeIdToDelete = 1; // John Doe is ID 1 in the initial data.

        // Perform the DELETE request for employee with ID 1.
        mockMvc.perform(delete("/employees/{id}", employeeIdToDelete))
                .andDo(print())
                .andExpect(status().isNoContent()); // Expect HTTP 204 No Content for successful deletion.

        LOGGER.info("Finished testDeleteEmployee_Success - Deletion successful for ID {}", employeeIdToDelete);

        // Verify that the employee is actually deleted by checking the list of all employees.
        mockMvc.perform(get("/employees"))
                .andDo(print())
                .andExpect(status().isOk()) // Expect HTTP 200 OK.
                .andExpect(jsonPath("$").isArray()) // Assert that the response is a JSON array.
                .andExpect(jsonPath("$", hasSize(2))) // Now, there should be 2 employees left (originally 3, one deleted).
                // Verify that the deleted employee's ID (1) is no longer present in the list of IDs.
                // Using Hamcrest 'not' and 'hasItem' for this negative assertion.
                .andExpect(jsonPath("$[*].id", not(hasItem(employeeIdToDelete))));

        LOGGER.info("Verification of deletion complete. List now has 2 employees.");
    }

    /**
     * Test case for attempting to delete a non-existent employee.
     * This tests the `EmployeeNotFoundException` handling via `GlobalExceptionHandler`.
     * @throws Exception if MockMvc operation fails.
     */
    @Test
    public void testDeleteEmployee_NotFound() throws Exception {
        LOGGER.info("Starting testDeleteEmployee_NotFound");

        Integer nonExistentEmployeeId = 999; // An ID that won't be in the initial list.

        // Perform the DELETE request for a non-existent employee.
        mockMvc.perform(delete("/employees/{id}", nonExistentEmployeeId))
                .andDo(print())
                .andExpect(status().isNotFound()) // Expect HTTP 404 Not Found.
                .andExpect(jsonPath("$.status").value(404)) // Assert 'status' field is 404.
                .andExpect(jsonPath("$.error").value("Not Found")) // Assert 'error' field is "Not Found".
                // Assert the specific error message from EmployeeNotFoundException, as formatted by GlobalExceptionHandler.
                .andExpect(jsonPath("$.message").value("Employee with ID " + nonExistentEmployeeId + " not found."));

        LOGGER.info("Finished testDeleteEmployee_NotFound");
    }
}