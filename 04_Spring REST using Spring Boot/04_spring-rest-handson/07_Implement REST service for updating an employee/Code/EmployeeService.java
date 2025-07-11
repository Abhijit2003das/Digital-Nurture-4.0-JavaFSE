// src/main/java/com/example/countryservice/EmployeeService.java
package com.example.countryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; // For dependency injection
import org.springframework.stereotype.Service;              // Annotation to mark this class as a Spring service

import java.util.List;

/**
 * Service layer for Employee operations.
 * Contains business logic and interacts with the EmployeeDao.
 */
@Service // Marks this class as a Spring service component.
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    // Inject EmployeeDao dependency. Spring will automatically provide an instance.
    @Autowired
    private EmployeeDao employeeDao;

    /**
     * Retrieves all employees from the data layer.
     * @return A list of all Employee objects.
     */
    public List<Employee> getAllEmployees() {
        LOGGER.info("Start: getAllEmployees() in EmployeeService.");
        // Delegate the call to the DAO layer
        List<Employee> employees = employeeDao.getAllEmployees();
        LOGGER.info("End: getAllEmployees() in EmployeeService. Found {} employees.", employees.size());
        return employees;
    }

    /**
     * Updates an existing employee's details.
     *
     * @param employee The Employee object with updated details.
     * @throws EmployeeNotFoundException if no employee with the given ID is found in the data layer.
     */
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("Start: updateEmployee() in EmployeeService for ID: {}", employee.getId());
        // Delegate the update operation to the DAO layer
        employeeDao.updateEmployee(employee);
        LOGGER.info("End: updateEmployee() in EmployeeService for ID: {}. Employee updated successfully.", employee.getId());
    }
}