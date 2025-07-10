package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Department; // Import your Department entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; // Import Optional for safe handling of possibly non-existent results

@Repository // Marks this interface as a Spring Data JPA repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    // JpaRepository<Entity, IdType>
    // Department: The entity class this repository manages
    // Integer: The data type of the primary key (ID) of the Department entity

    /**
     * Custom method to find a Department entity by its name.
     * Spring Data JPA will automatically implement this method based on the method name.
     *
     * @param name The name of the department to find.
     * @return An Optional containing the Department if found, or an empty Optional if not.
     */
    Optional<Department> findByName(String name);
}