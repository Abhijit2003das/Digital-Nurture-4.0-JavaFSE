package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class OrmLearnApplication implements CommandLineRunner {

    @Autowired
    private EmployeeService employeeService;

    public static void main(String[] args) {
        SpringApplication.run(OrmLearnApplication.class, args);
    }

    @Override
    public void run(String... args) {
        testGetAllEmployeesWithSkills();
    }

    public void testGetAllEmployeesWithSkills() {
        List<Employee> employees = employeeService.getAllEmployeesWithSkills();
        for (Employee emp : employees) {
            System.out.println(emp);
            System.out.println("Skills: " + emp.getSkillList()); // ✅ Safe to print now
        }
    }
}
