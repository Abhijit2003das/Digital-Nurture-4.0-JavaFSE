package com.ems.repository;

import com.ems.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByName(String name);

    List<Employee> findByNameContainingIgnoreCase(String name);

    List<Employee> findByDepartmentId(Long departmentId);

    @Query("SELECT e FROM Employee e WHERE e.email LIKE %:domain")
    List<Employee> findByEmailDomain(@Param("domain") String domain);

    @Query(name = "Employee.findByEmail")
    Employee findByEmail(@Param("email") String email);
}