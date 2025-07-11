// src/main/java/com/example/countryservice/EmployeeDao.java
package com.example.countryservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Data Access Object (DAO) for Employee entities.
 * Simulates database operations using an in-memory List.
 * This class uses static lists to simulate a single, shared data source for the application.
 */
@Repository // Marks this class as a Spring repository component.
public class EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);

    // In-memory static lists to store data, simulating database tables.
    // Being static, they retain their state across different instances of EmployeeDao
    // and across different requests within the application's lifecycle.
    private static List<Employee> EMPLOYEE_LIST = new ArrayList<>();
    private static List<Department> DEPARTMENT_LIST = new ArrayList<>();
    private static List<Skill> SKILL_LIST = new ArrayList<>();

    /**
     * Static initializer block. This block runs once when the EmployeeDao class
     * is first loaded into the Java Virtual Machine (JVM).
     * It's used here to populate initial dummy data, simulating a database with
     * pre-existing records.
     */
    static {
        LOGGER.info("Initializing static EmployeeDao data.");
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
        // Note: Date constructor (year, month, day) uses year from 1900 and 0-indexed months.
        // For example, new Date(90, 4, 15) is May 15, 1990.
        EMPLOYEE_LIST.add(new Employee(
            1, "John Doe", 75000.00, true, new Date(90, 4, 15),
            itDept, List.of(javaSkill, springSkill, sqlSkill)
        ));

        EMPLOYEE_LIST.add(new Employee(
            2, "Jane Smith", 82000.50, true, new Date(92, 7, 20),
            salesDept, List.of(communicationSkill, marketingSkill)
        ));

        EMPLOYEE_LIST.add(new Employee(
            3, "Alice Johnson", 60000.00, false, new Date(95, 1, 10),
            hrDept, List.of(communicationSkill, sqlSkill)
        ));

        LOGGER.info("Initial EMPLOYEE_LIST populated successfully.");
    }

    /**
     * Retrieves all employees from the in-memory list.
     * @return A new ArrayList containing all Employee objects, preventing external modification of the static list.
     */
    public List<Employee> getAllEmployees() {
        LOGGER.info("Start: getAllEmployees() in EmployeeDao.");
        return new ArrayList<>(EMPLOYEE_LIST);
    }

    /**
     * Updates an existing employee's details in the in-memory list.
     * It iterates through the list to find the employee by ID and replaces the old object.
     * @param employee The Employee object with updated details. The 'id' field is used to find the employee.
     * @throws EmployeeNotFoundException if no employee with the given ID is found for update.
     */
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("Start: updateEmployee() in EmployeeDao for ID: {}", employee.getId());
        boolean found = false;
        for (int i = 0; i < EMPLOYEE_LIST.size(); i++) {
            if (EMPLOYEE_LIST.get(i).getId().equals(employee.getId())) {
                EMPLOYEE_LIST.set(i, employee); // Replace the existing employee object at this index.
                found = true;
                LOGGER.info("Employee with ID {} updated successfully in DAO.", employee.getId());
                break; // Employee found and updated, no need to continue iterating.
            }
        }

        if (!found) {
            LOGGER.warn("Employee with ID {} not found in DAO for update.", employee.getId());
            throw new EmployeeNotFoundException(employee.getId());
        }
        LOGGER.info("End: updateEmployee() in EmployeeDao.");
    }

    /**
     * Deletes an employee by their ID from the in-memory list.
     * Uses an Iterator for safe removal of elements while iterating over a List.
     * @param id The ID of the employee to delete.
     * @throws EmployeeNotFoundException if no employee with the given ID is found for deletion.
     */
    public void deleteEmployee(Integer id) throws EmployeeNotFoundException {
        LOGGER.info("Start: deleteEmployee() in EmployeeDao for ID: {}", id);
        boolean found = false;
        Iterator<Employee> iterator = EMPLOYEE_LIST.iterator();
        while (iterator.hasNext()) {
            Employee employee = iterator.next();
            if (employee.getId().equals(id)) {
                iterator.remove(); // Safely remove the current element from the list.
                found = true;
                LOGGER.info("Employee with ID {} deleted successfully from DAO.", id);
                break; // Employee found and removed, no need to continue iterating.
            }
        }

        if (!found) {
            LOGGER.warn("Employee with ID {} not found in DAO for deletion.", id);
            throw new EmployeeNotFoundException(id);
        }
        LOGGER.info("End: deleteEmployee() in EmployeeDao.");
    }

    // --- NEW: Public static getters for testing purposes ---
    // These methods allow test classes to access and manipulate the static lists
    // to ensure proper test isolation and setup/teardown of data.
    public static List<Employee> getEMPLOYEE_LIST() {
        return EMPLOYEE_LIST;
    }

    public static List<Department> getDEPARTMENT_LIST() {
        return DEPARTMENT_LIST;
    }

    public static List<Skill> getSKILL_LIST() {
        return SKILL_LIST;
    }
}