package com.ems.repository;

import com.ems.dto.EmployeeDTO;
import com.ems.model.Employee;
import com.ems.projection.EmployeeNameEmailProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // 🔍 Search by name
    @Query("SELECT e FROM Employee e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Employee> searchByName(@Param("name") String name);

    // 📌 Interface-based projection
    @Query("SELECT e.name AS name, e.email AS email FROM Employee e")
    List<EmployeeNameEmailProjection> findAllNameAndEmail();

    // 🧾 DTO-based projection
    @Query("SELECT new com.ems.dto.EmployeeDTO(e.name, e.email, e.department.name) FROM Employee e")
    List<EmployeeDTO> findAllEmployeeDTOs();

    // 📄 Pagination method
    Page<Employee> findAll(Pageable pageable);
}
