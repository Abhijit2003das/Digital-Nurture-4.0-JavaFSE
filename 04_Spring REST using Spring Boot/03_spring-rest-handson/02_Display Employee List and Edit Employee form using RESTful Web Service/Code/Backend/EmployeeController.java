package net.javaguides.springboot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Optional; // <<< ADD THIS IMPORT STATEMENT

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

import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Department;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.model.Skill;
import net.javaguides.springboot.repository.DepartmentRepository;
import net.javaguides.springboot.repository.EmployeeRepository;
import net.javaguides.springboot.repository.SkillRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private SkillRepository skillRepository;

    // get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }        

    // create employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee) {
        // --- Handle Department before saving Employee ---
        if (employee.getDepartment() != null && employee.getDepartment().getName() != null && !employee.getDepartment().getName().trim().isEmpty()) {
            String departmentName = employee.getDepartment().getName().trim();
            // Try to find an existing department by name
            Optional<Department> existingDepartment = departmentRepository.findByName(departmentName);

            if (existingDepartment.isPresent()) {
                // If department exists, use the existing one
                employee.setDepartment(existingDepartment.get());
            } else {
                // If department does not exist, create a new one
                Department newDepartment = new Department(departmentName); // This now calls the new constructor
                departmentRepository.save(newDepartment);
                employee.setDepartment(newDepartment);
            }
        } else {
            employee.setDepartment(null);
        }

        // --- Handle Skills before saving Employee ---
        if (employee.getSkills() != null && !employee.getSkills().isEmpty()) {
            List<Skill> managedSkills = new ArrayList<>();
            for (Skill skill : employee.getSkills()) {
                if (skill.getName() != null && !skill.getName().trim().isEmpty()) {
                    String skillName = skill.getName().trim();
                    // Try to find an existing skill by name
                    Optional<Skill> existingSkill = skillRepository.findByName(skillName);

                    if (existingSkill.isPresent()) {
                        // If skill exists, use the existing one
                        managedSkills.add(existingSkill.get());
                    } else {
                        // If skill does not exist, create a new one
                        Skill newSkill = new Skill(skillName); // Uses the constructor that takes only name (assuming you updated Skill.java as advised)
                        skillRepository.save(newSkill); // save() should now be found
                        managedSkills.add(newSkill);
                    }
                }
            }
            employee.setSkills(managedSkills);
        } else {
            employee.setSkills(new ArrayList<>());
        }

        // Save the employee (Department and Skills are now handled)
        return employeeRepository.save(employee);
    }

    // get employee by id rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        return ResponseEntity.ok(employee);
    }

    // update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employeeDetails){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exist with id :" + id));
        
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmail(employeeDetails.getEmail());

        // --- Update Department (similar logic to create) ---
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

        // --- Update Skills (similar logic to create) ---
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
        employee.setSkills(managedSkills);
        
        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    // delete employee rest api
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