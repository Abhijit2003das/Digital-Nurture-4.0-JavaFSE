// src/main/java/com/example/userservice/repository/UserRepository.java
package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Marks this interface as a Spring Data JPA repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository provides basic CRUD operations (save, findById, findAll, delete, etc.)
    // We can also define custom query methods
    Optional<User> findByUsername(String username);
}