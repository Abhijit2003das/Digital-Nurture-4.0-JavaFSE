package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.model.Skill;
import com.cognizant.ormlearn.service.EmployeeService;
import com.cognizant.ormlearn.service.SkillService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class OrmLearnApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SkillService skillService;

    public static void main(String[] args) {
        SpringApplication.run(OrmLearnApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Uncomment only this method during test
        testAddSkillToEmployee();
    }

    private void testAddSkillToEmployee() {
        LOGGER.info("Start testAddSkillToEmployee");

        Employee employee = employeeService.get(1); // Ensure ID 1 exists
        Skill skill = skillService.get(2);           // Ensure ID 2 exists

        employee.getSkillList().add(skill);
        employeeService.save(employee);

        LOGGER.debug("Updated Employee: {}", employee);
        LOGGER.debug("Skills: {}", employee.getSkillList());

        LOGGER.info("End testAddSkillToEmployee");
    }
}
