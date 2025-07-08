package com.ems.employee.service;

import com.ems.employee.model.Employee;
import java.util.List;

public interface EmployeeService {
    Employee save(Employee employee);
    List<Employee> saveAllInBatch(List<Employee> employees);
    List<Employee> getAll();
}
