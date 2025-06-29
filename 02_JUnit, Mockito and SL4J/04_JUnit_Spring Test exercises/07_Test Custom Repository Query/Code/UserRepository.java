package com.example.usertest;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // Import List

/**
 * Repository interface for User entity.
 * Extends JpaRepository to get basic CRUD operations.
 * The generic parameters are: <Entity_Class, ID_Type>.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Custom query method to find users by their name.
     * Spring Data JPA automatically generates the query based on the method name.
     *
     * @param name The name to search for.
     * @return A list of User objects matching the given name.
     */
    List<User> findByName(String name);
}
