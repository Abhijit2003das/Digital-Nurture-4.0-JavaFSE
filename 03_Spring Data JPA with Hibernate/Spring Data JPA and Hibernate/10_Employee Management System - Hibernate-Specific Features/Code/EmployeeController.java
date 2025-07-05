package com.ems.controller;

import com.ems.employee.model.Employee;
import com.ems.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAll();
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    @PostMapping("/batch")
    public List<Employee> createEmployeesInBatch(@RequestBody List<Employee> employees) {
        return employeeService.saveAllInBatch(employees);
    }
}
