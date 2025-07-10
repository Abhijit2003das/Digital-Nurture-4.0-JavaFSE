package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Optional; // Required for Optional

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.javaguides.springboot.exception.ResourceNotFoundException; // Required for custom exception
import net.javaguides.springboot.model.Department; // Required for Department entity
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Skill;     // Required for Skill entity
import net.javaguides.springboot.repository.DepartmentRepository; // Required for DepartmentRepository
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.repository.SkillRepository;     // Required for SkillRepository
import net.javaguides.springboot.service.EmployeeService; // Required for EmployeeService

@CrossOrigin(origins = "http://localhost:4200") // Allows requests from your Angular frontend
@RestController // Marks this class as a REST controller
@RequestMapping("/api/v1/") // Base path for all endpoints in this controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository; // Injects EmployeeRepository for direct DB operations

    @Autowired
    private DepartmentRepository departmentRepository; // Injects DepartmentRepository for managing departments

    @Autowired
    private SkillRepository skillRepository;     // Injects SkillRepository for managing skills

    @Autowired
    private EmployeeService employeeService; // Injects EmployeeService for business logic

    /**
     * GET /api/v1/employees
     * Retrieves a list of all employees. Delegates to the EmployeeService.
     * @return A list of Employee objects.
     */
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees(); // Calls the service layer
    }

    /**
     * POST /api/v1/employees
     * Creates a new employee. Handles associated Department and Skill entities.
     * @param employee The Employee object to be created (from request body).
     * @return The created Employee object.
     */
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        // --- Handle Department: Find existing or create new ---
        if (employee.getDepartment() != null && employee.getDepartment().getName() != null && !employee.getDepartment().getName().trim().isEmpty()) {
            String departmentName = employee.getDepartment().getName().trim();
            Optional<Department> existingDepartment = departmentRepository.findByName(departmentName);

            if (existingDepartment.isPresent()) {
                employee.setDepartment(existingDepartment.get()); // Use existing department
            } else {
                Department newDepartment = new Department(departmentName); // Create new department
                departmentRepository.save(newDepartment); // Save new department to get its auto-generated ID
                employee.setDepartment(newDepartment);
            }
        } else {
            employee.setDepartment(null); // Ensure department is null if no name provided
        }

        // --- Handle Skills: Find existing or create new ---
        if (employee.getSkills() != null && !employee.getSkills().isEmpty()) {
            List<Skill> managedSkills = new ArrayList<>();
            for (Skill skill : employee.getSkills()) {
                if (skill.getName() != null && !skill.getName().trim().isEmpty()) {
                    String skillName = skill.getName().trim();
                    Optional<Skill> existingSkill = skillRepository.findByName(skillName);

                    if (existingSkill.isPresent()) {
                        managedSkills.add(existingSkill.get()); // Use existing skill
                    } else {
                        Skill newSkill = new Skill(skillName); // Create new skill
                        skillRepository.save(newSkill); // Save new skill to get its auto-generated ID
                        managedSkills.add(newSkill);
                    }
                }
            }
            employee.setSkills(managedSkills); // Set the list of managed (existing or newly created) skills
        } else {
            employee.setSkills(new ArrayList<>()); // Ensure skills list is empty if no skills provided
        }

        // Save the employee (Department and Skills are now correctly associated)
        return employeeRepository.save(employee);
    }

    /**
     * GET /api/v1/employees/{id}
     * Retrieves an employee by their ID.
     * @param id The ID of the employee to retrieve.
     * @return ResponseEntity containing the Employee object if found, or 404 Not Found.
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(employee);
    }

    /**
     * PUT /api/v1/employees/{id}
     * Updates an existing employee. Handles associated Department and Skill entities.
     * @param id The ID of the employee to update.
     * @param employeeDetails The updated Employee object (from request body).
     * @return ResponseEntity containing the updated Employee object.
     */
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employeeDetails){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        // --- Update Department: Find existing or create new (similar to create) ---
        if (employeeDetails.getDepartment() != null && employeeDetails.getDepartment().getName() != null && !employeeDetails.getDepartment().getName().trim().isEmpty()) {
            String departmentName = employeeDetails.getDepartment().getName().trim();
            Optional<Department> existingDepartment = departmentRepository.findByName(departmentName);
            if (existingDepartment.isPresent()) {
                employee.setDepartment(existingDepartment.get());
            } else {
                Department newDepartment = new Department(departmentName);
                departmentRepository.save(newDepartment);
                employee.setDepartment(newDepartment);
            }
        } else {
            employee.setDepartment(null);
        }

        // --- Update Skills: Find existing or create new (similar to create) ---
        List<Skill> managedSkills = new ArrayList<>();
        if (employeeDetails.getSkills() != null && !employeeDetails.getSkills().isEmpty()) {
            for (Skill skill : employeeDetails.getSkills()) {
                if (skill.getName() != null && !skill.getName().trim().isEmpty()) {
                    String skillName = skill.getName().trim();
                    Optional<Skill> existingSkill = skillRepository.findByName(skillName);
                    if (existingSkill.isPresent()) {
                        managedSkills.add(existingSkill.get());
                    } else {
                        Skill newSkill = new Skill(skillName);
                        skillRepository.save(newSkill);
                        managedSkills.add(newSkill);
                    }
                }
            }
        }
        employee.setSkills(managedSkills); // Set the updated list of skills

        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    /**
     * DELETE /api/v1/employees/{id}
     * Deletes an employee by their ID.
     * @param id The ID of the employee to delete.
     * @return ResponseEntity with a confirmation map.
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable String id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        
        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}