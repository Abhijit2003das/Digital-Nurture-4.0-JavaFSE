package com.ems.repository;

import com.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    List<Employee> findByDepartmentName(String name);
    List<Employee> findByEmailContaining(String email);

    @Query("SELECT e FROM Employee e WHERE e.name = :name")
    List<Employee> findByNameCustom(String name);
}
