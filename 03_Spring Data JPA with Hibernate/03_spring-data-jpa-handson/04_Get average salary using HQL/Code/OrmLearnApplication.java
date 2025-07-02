package com.cognizant.ormlearn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cognizant.ormlearn.service.EmployeeService;

@SpringBootApplication
public class OrmLearnApplication implements CommandLineRunner {

    @Autowired
    private EmployeeService employeeService;

    public static void main(String[] args) {
        SpringApplication.run(OrmLearnApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        testGetAverageSalary();
    }

    private void testGetAverageSalary() {
        int departmentId = 1; // Change as per your database data
        Double avgSalary = employeeService.getAverageSalary(departmentId);
        if (avgSalary != null) {
            System.out.println("Average salary for Department ID " + departmentId + ": " + avgSalary);
        } else {
            System.out.println("No employees found in Department ID " + departmentId);
        }
    }
}
