package net.javaguides.springboot.controller;

import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.service.DepartmentService; // Import DepartmentService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200") // Allow requests from your Angular frontend
@RestController // Marks this class as a REST controller
@RequestMapping("/api/v1/") // Base path for all endpoints in this controller
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService; // Inject DepartmentService

    /**
     * GET /api/v1/departments
     * Retrieves a list of all departments.
     * @return A list of Department objects.
     */
    @GetMapping("/departments") // Map this method to GET requests at /api/v1/departments
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments(); // Delegate to the service layer
    }

    // You can add other department-related endpoints here (e.g., create, update, delete)
    // if your application requires them in a separate DepartmentController.
}