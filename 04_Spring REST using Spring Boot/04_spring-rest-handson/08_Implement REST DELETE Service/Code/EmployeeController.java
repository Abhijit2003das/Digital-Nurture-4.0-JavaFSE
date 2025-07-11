// src/main/java/com/example/countryservice/EmployeeController.java
package com.example.countryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;       // NEW: For HttpStatus.NO_CONTENT
import org.springframework.http.ResponseEntity;   // NEW: For returning ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping; // NEW: For HTTP DELETE method
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // NEW: To extract ID from URL path
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

import java.util.List;

/**
 * REST Controller for Employee operations.
 * Handles HTTP requests related to employees.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PutMapping
    public void updateEmployee(@RequestBody @Valid Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("Start: updateEmployee() in EmployeeController.");
        LOGGER.info("Received Employee data for update: {}", employee);

        employeeService.updateEmployee(employee);

        LOGGER.info("End: updateEmployee() in EmployeeController. Employee ID {} updated successfully.", employee.getId());
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        LOGGER.info("Start: getAllEmployees() in EmployeeController.");
        List<Employee> employees = employeeService.getAllEmployees();
        LOGGER.info("End: getAllEmployees() in EmployeeController. Returning {} employees.", employees.size());
        return employees;
    }

    /**
     * NEW METHOD: Handles HTTP DELETE requests to remove an employee by ID.
     * Returns 204 No Content for successful deletion, which is a standard REST practice.
     *
     * @param id The ID of the employee to be deleted, extracted from the URL path.
     * @return ResponseEntity<Void> with HTTP 204 No Content status.
     * @throws EmployeeNotFoundException If an employee with the given ID is not found.
     */
    @DeleteMapping("/{id}") // Maps HTTP DELETE requests to /employees/{id}
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) throws EmployeeNotFoundException {
        LOGGER.info("Start: deleteEmployee() in EmployeeController for ID: {}", id);

        employeeService.deleteEmployee(id); // Delegate to the service layer for deletion.

        LOGGER.info("End: deleteEmployee() in EmployeeController. Employee ID {} deleted successfully.", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 No Content on success.
    }
}