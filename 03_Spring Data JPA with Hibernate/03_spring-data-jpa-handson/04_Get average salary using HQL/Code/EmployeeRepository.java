package com.cognizant.ormlearn.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cognizant.ormlearn.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Get average salary of all employees
    @Query("SELECT AVG(e.salary) FROM Employee e")
    Double getAverageSalary(); // ✅ Changed to wrapper type

    // Get average salary of employees by department ID
    @Query("SELECT AVG(e.salary) FROM Employee e WHERE e.department.id = :id")
    Double getAverageSalary(@Param("id") int id); // ✅ Changed to wrapper type
}
