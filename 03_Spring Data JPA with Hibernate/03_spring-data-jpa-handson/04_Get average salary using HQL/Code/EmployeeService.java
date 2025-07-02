package com.cognizant.ormlearn.service;

public interface EmployeeService {
    Double getAverageSalary(int deptId); // Use wrapper Double to allow null return
}
