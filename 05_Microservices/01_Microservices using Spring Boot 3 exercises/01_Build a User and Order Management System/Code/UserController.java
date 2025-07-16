// src/main/java/com/example/userservice/controller/UserController.java
package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/users") // Base path for all endpoints in this controller
public class UserController {

    private final UserService userService;

    @Autowired // Injects UserService dependency
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping // Handles GET requests to /api/users
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}") // Handles GET requests to /api/users/{id}
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok) // If user found, return 200 OK with user
                .orElse(ResponseEntity.notFound().build()); // If not found, return 404 Not Found
    }

    @GetMapping("/by-username/{username}") // Handles GET requests to /api/users/by-username/{username}
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping // Handles POST requests to /api/users
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED); // Return 201 Created
    }

    @PutMapping("/{id}") // Handles PUT requests to /api/users/{id}
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        try {
            User updatedUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updatedUser); // Return 200 OK with updated user
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found if user doesn't exist
        }
    }

    @DeleteMapping("/{id}") // Handles DELETE requests to /api/users/{id}
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }
}