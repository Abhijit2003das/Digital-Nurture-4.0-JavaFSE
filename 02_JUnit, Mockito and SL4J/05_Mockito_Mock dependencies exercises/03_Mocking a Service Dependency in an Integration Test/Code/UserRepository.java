package com.example.userapp.repository;

import com.example.userapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository marks this interface as a Spring Data JPA repository.
// It extends JpaRepository, providing standard CRUD operations for the User entity
// with Long as the type of its primary key.
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository automatically provides methods like findById, save, findAll, delete, etc.
    // No custom methods are needed for this example.
}
