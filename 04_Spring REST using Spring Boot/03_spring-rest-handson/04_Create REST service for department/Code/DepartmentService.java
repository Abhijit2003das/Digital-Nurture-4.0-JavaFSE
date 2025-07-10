package net.javaguides.springboot.service;

import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.repository.DepartmentRepository; // Import DepartmentRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Import @Transactional

import java.util.List;

@Service // Marks this class as a Spring Service component
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository; // Inject DepartmentRepository

    /**
     * Retrieves a list of all departments from the database.
     * @return A list of Department objects.
     */
    @Transactional(readOnly = true) // Optimize for read-only operations
    public List<Department> getAllDepartments() {
        // This invokes departmentRepository.findAll() directly, as JpaRepository provides it.
        return departmentRepository.findAll();
    }
}