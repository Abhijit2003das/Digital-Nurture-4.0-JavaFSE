package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; // Import @Service
import org.springframework.transaction.annotation.Transactional; // Import @Transactional

import java.util.List;

@Service // Marks this class as a Spring Service component
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository; // Injects the EmployeeRepository

    /**
     * Retrieves a list of all employees from the database.
     * This method is marked as @Transactional to ensure it runs within a database transaction.
     * @return A list of all Employee objects.
     */
    @Transactional(readOnly = true) // readOnly = true can optimize read operations
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // You can add other service-level methods here if your application requires
    // more complex business logic before interacting with the repository directly.
    // For example:
    // @Transactional
    // public Employee saveEmployee(Employee employee) {
    //     // Add business rules/validation here before saving
    //     return employeeRepository.save(employee);
    // }
}