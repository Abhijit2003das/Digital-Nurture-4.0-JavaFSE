package com.ems.repository;

import com.ems.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // Supports case-insensitive name search with pagination and sorting
    Page<Employee> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
