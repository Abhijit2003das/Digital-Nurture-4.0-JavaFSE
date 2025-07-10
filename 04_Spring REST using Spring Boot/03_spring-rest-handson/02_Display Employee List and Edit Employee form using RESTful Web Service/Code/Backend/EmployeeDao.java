package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Employee; // Import from the correct model package
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct; // Use jakarta for Spring Boot 3+

import java.util.ArrayList;
import java.util.List;

@Repository // Marks this class as a Spring repository component
@ImportResource("classpath:employee.xml") // Tells Spring Boot to load beans from employee.xml
public class EmployeeDao {

    // Static variable to hold the employee list
    private static List<Employee> EMPLOYEE_LIST;

    // Autowire ApplicationContext to get beans from XML
    private final ApplicationContext applicationContext;

    // Constructor injection for ApplicationContext
    public EmployeeDao(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    // @PostConstruct ensures this method runs after dependency injection is done
    @PostConstruct
    public void init() {
        if (EMPLOYEE_LIST == null) {
            System.out.println("Initializing EmployeeDao: Loading employee list from employee.xml via Spring Context.");
            // Retrieve the ArrayList bean configured in employee.xml
            EMPLOYEE_LIST = (ArrayList<Employee>) applicationContext.getBean("employeeList");
            System.out.println("Loaded " + EMPLOYEE_LIST.size() + " employees from XML.");
        }
    }

    // Method to return the employee list
    public List<Employee> getAllEmployees() {
        return EMPLOYEE_LIST;
    }
}