package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployeesWithSkills();
}
