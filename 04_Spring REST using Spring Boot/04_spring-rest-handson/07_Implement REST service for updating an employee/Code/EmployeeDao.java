// src/main/java/com/example/countryservice/EmployeeDao.java
package com.example.countryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository; // Annotation to mark this class as a Spring repository

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors; // For stream API operations

/**
 * Data Access Object (DAO) for Employee entities.
 * Simulates database operations using an in-memory List.
 */
@Repository // Marks this class as a Spring repository, making it a Spring component.
public class EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);

    // In-memory list to store employee data, simulating a database table.
    private static List<Employee> EMPLOYEE_LIST = new ArrayList<>();
    // In-memory list to store department data (for initial employee setup)
    private static List<Department> DEPARTMENT_LIST = new ArrayList<>();
    // In-memory list to store skill data (for initial employee setup)
    private static List<Skill> SKILL_LIST = new ArrayList<>();

    // Static initializer block to populate initial dummy data when the class is loaded.
    static {
        // --- Initialize Departments ---
        Department hrDept = new Department(1, "Human Resources");
        Department itDept = new Department(2, "Information Technology");
        Department salesDept = new Department(3, "Sales");
        DEPARTMENT_LIST.add(hrDept);
        DEPARTMENT_LIST.add(itDept);
        DEPARTMENT_LIST.add(salesDept);

        // --- Initialize Skills ---
        Skill javaSkill = new Skill(101, "Java");
        Skill springSkill = new Skill(102, "Spring Boot");
        Skill sqlSkill = new Skill(103, "SQL");
        Skill communicationSkill = new Skill(104, "Communication");
        Skill marketingSkill = new Skill(105, "Digital Marketing");
        SKILL_LIST.add(javaSkill);
        SKILL_LIST.add(springSkill);
        SKILL_LIST.add(sqlSkill);
        SKILL_LIST.add(communicationSkill);
        SKILL_LIST.add(marketingSkill);

        // --- Initialize Employees ---
        // Employee 1
        EMPLOYEE_LIST.add(new Employee(
            1, "John Doe", 75000.00, true, new Date(90, 4, 15), // May 15, 1990 (Year 90 is 1990 from 1900)
            itDept, List.of(javaSkill, springSkill, sqlSkill)
        ));

        // Employee 2
        EMPLOYEE_LIST.add(new Employee(
            2, "Jane Smith", 82000.50, true, new Date(92, 7, 20), // Aug 20, 1992
            salesDept, List.of(communicationSkill, marketingSkill)
        ));

        // Employee 3
        EMPLOYEE_LIST.add(new Employee(
            3, "Alice Johnson", 60000.00, false, new Date(95, 1, 10), // Feb 10, 1995
            hrDept, List.of(communicationSkill, sqlSkill)
        ));

        // Log initial data for debugging
        LOGGER.info("Initial EMPLOYEE_LIST populated: {}", EMPLOYEE_LIST);
    }


    /**
     * Retrieves all employees from the in-memory list.
     * @return A list of all Employee objects.
     */
    public List<Employee> getAllEmployees() {
        LOGGER.info("Start: getAllEmployees() in EmployeeDao.");
        // Return a new ArrayList to prevent external modification of the static list
        return new ArrayList<>(EMPLOYEE_LIST);
    }

    /**
     * Updates an existing employee's details in the in-memory list.
     *
     * @param employee The Employee object with updated details. The 'id' field is used to find the employee.
     * @throws EmployeeNotFoundException if no employee with the given ID is found.
     */
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("Start: updateEmployee() in EmployeeDao for ID: {}", employee.getId());
        boolean found = false;
        for (int i = 0; i < EMPLOYEE_LIST.size(); i++) {
            if (EMPLOYEE_LIST.get(i).getId().equals(employee.getId())) {
                // Update the existing employee object at this index
                EMPLOYEE_LIST.set(i, employee);
                found = true;
                LOGGER.info("Employee with ID {} updated successfully in DAO.", employee.getId());
                break;
            }
        }

        if (!found) {
            LOGGER.warn("Employee with ID {} not found in DAO for update.", employee.getId());
            throw new EmployeeNotFoundException(employee.getId());
        }
        LOGGER.info("End: updateEmployee() in EmployeeDao.");
    }
}