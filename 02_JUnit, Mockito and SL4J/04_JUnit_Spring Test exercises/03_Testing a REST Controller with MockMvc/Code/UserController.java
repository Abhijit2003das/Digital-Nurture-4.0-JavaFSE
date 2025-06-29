package com.example.usertest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for managing User resources.
 * Handles HTTP requests related to user data.
 */
@RestController // Marks this class as a REST controller, handling incoming web requests
@RequestMapping("/users") // Base URL path for all endpoints in this controller
public class UserController {

    private final UserService userService; // Using final for immutability and constructor injection

    /**
     * Constructs a UserController with the given UserService.
     * Spring will automatically inject an instance of UserService here.
     *
     * @param userService The UserService dependency.
     */
    // @Autowired // @Autowired is optional for single constructor injection since Spring 4.3+
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles GET requests to retrieve a user by their ID.
     *
     * @param id The ID of the user to retrieve, extracted from the URL path.
     * @return ResponseEntity containing the User object if found, or an appropriate HTTP status.
     */
    @GetMapping("/{id}") // Maps GET requests to /users/{id}
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        // Call the service to get the user by ID
        User user = userService.getUserById(id);

        // Check if the user was found
        if (user != null) {
            // If user is found, return HTTP 200 OK with the user object in the body
            return ResponseEntity.ok(user);
        } else {
            // If user is not found, return HTTP 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
}
