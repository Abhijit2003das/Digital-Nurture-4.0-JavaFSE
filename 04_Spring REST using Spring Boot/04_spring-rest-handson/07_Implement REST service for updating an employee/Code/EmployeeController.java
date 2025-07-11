// src/main/java/com/example/countryservice/EmployeeController.java
package com.example.countryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; // For dependency injection
import org.springframework.web.bind.annotation.GetMapping;   // For GET HTTP method
import org.springframework.web.bind.annotation.PutMapping;   // For PUT HTTP method
import org.springframework.web.bind.annotation.RequestBody;  // To bind request body to Employee object
import org.springframework.web.bind.annotation.RequestMapping; // To define base URL path
import org.springframework.web.bind.annotation.RestController; // To mark this as a REST controller

import jakarta.validation.Valid; // For automatic validation of @RequestBody

import java.util.List;

/**
 * REST Controller for Employee operations.
 * Handles HTTP requests related to employees.
 */
@RestController // Marks this class as a REST Controller, handling incoming web requests.
@RequestMapping("/employees") // Base URL path for all employee-related endpoints in this controller.
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    // Inject EmployeeService dependency. Spring will automatically provide an instance.
    @Autowired
    private EmployeeService employeeService;

    /**
     * Handles HTTP PUT requests to update an existing employee.
     * URL Guideline: Since the employee ID is part of the request body,
     * the endpoint for updating an entire resource is commonly just "/employees".
     *
     * @param employee The Employee object containing the updated details.
     * The @RequestBody annotation binds the JSON request body to this object.
     * The @Valid annotation triggers validation based on rules in Employee.java.
     * @throws EmployeeNotFoundException If an employee with the given ID is not found for update.
     */
    @PutMapping // Maps HTTP PUT requests to the "/employees" path (relative to @RequestMapping).
    public void updateEmployee(@RequestBody @Valid Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("Start: updateEmployee() in EmployeeController.");
        LOGGER.info("Received Employee data for update: {}", employee);

        employeeService.updateEmployee(employee); // Delegate to the service layer for update operation.

        LOGGER.info("End: updateEmployee() in EmployeeController. Employee ID {} updated successfully.", employee.getId());
    }

    /**
     * Handles HTTP GET requests to retrieve all employees.
     * This endpoint is useful for verifying updates made via the PUT endpoint.
     *
     * @return A list of all Employee objects.
     */
    @GetMapping // Maps HTTP GET requests to the "/employees" path.
    public List<Employee> getAllEmployees() {
        LOGGER.info("Start: getAllEmployees() in EmployeeController.");
        List<Employee> employees = employeeService.getAllEmployees(); // Delegate to the service layer.
        LOGGER.info("End: getAllEmployees() in EmployeeController. Returning {} employees.", employees.size());
        return employees;
    }
}