package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Department;
import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.service.DepartmentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Set;

@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    @Autowired
    private DepartmentService departmentService;

    public static void main(String[] args) {
        SpringApplication.run(OrmLearnApplication.class, args);
    }

    @Autowired
    public void runTest() {
        LOGGER.info("Start");
        testGetDepartment();
        // comment other test methods if any
        LOGGER.info("End");
    }

    public void testGetDepartment() {
        Department department = departmentService.get(1); // Use a department ID that has multiple employees
        LOGGER.debug("Department: {}", department);
        Set<Employee> employeeList = department.getEmployeeList();
        LOGGER.debug("Employees in Department:");
        employeeList.forEach(emp -> LOGGER.debug("{}", emp));
    }
}
