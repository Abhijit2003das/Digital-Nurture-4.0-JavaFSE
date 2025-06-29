package com.example.usertest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus; // Import HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Import PostMapping
import org.springframework.web.bind.annotation.RequestBody; // Import RequestBody
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for managing User resources.
 * Handles HTTP requests related to user data.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles GET requests to retrieve a user by their ID.
     *
     * @param id The ID of the user to retrieve, extracted from the URL path.
     * @return ResponseEntity containing the User object if found, or an appropriate HTTP status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Handles POST requests to create a new user.
     *
     * @param user The User object received in the request body.
     * @return ResponseEntity containing the newly created user object with its generated ID.
     */
    @PostMapping // Maps POST requests to /users
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        // It's common practice to return HTTP 201 Created for resource creation
        // and include the Location header pointing to the new resource.
        // For simplicity here, we'll return 200 OK.
        // For 201 Created, you'd typically do:
        // return ResponseEntity.created(URI.create("/users/" + savedUser.getId())).body(savedUser);
        return ResponseEntity.ok(savedUser);
    }
}
