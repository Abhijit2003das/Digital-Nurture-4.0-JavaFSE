// src/main/java/com/example/countryservice/EmployeeService.java
package com.example.countryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Employee operations.
 * Contains business logic and interacts with the EmployeeDao.
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeDao employeeDao;

    public List<Employee> getAllEmployees() {
        LOGGER.info("Start: getAllEmployees() in EmployeeService.");
        List<Employee> employees = employeeDao.getAllEmployees();
        LOGGER.info("End: getAllEmployees() in EmployeeService. Found {} employees.", employees.size());
        return employees;
    }

    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("Start: updateEmployee() in EmployeeService for ID: {}", employee.getId());
        employeeDao.updateEmployee(employee);
        LOGGER.info("End: updateEmployee() in EmployeeService for ID: {}. Employee updated successfully.", employee.getId());
    }

    /**
     * NEW METHOD: Deletes an employee by their ID.
     * Delegates the operation to the DAO layer.
     *
     * @param id The ID of the employee to delete.
     * @throws EmployeeNotFoundException if no employee with the given ID is found.
     */
    public void deleteEmployee(Integer id) throws EmployeeNotFoundException {
        LOGGER.info("Start: deleteEmployee() in EmployeeService for ID: {}", id);
        employeeDao.deleteEmployee(id);
        LOGGER.info("End: deleteEmployee() in EmployeeService for ID: {}. Employee deleted successfully.", id);
    }
}