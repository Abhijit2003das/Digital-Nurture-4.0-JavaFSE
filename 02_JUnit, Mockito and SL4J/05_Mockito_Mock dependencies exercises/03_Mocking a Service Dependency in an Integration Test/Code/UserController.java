package com.example.userapp.controller;

import com.example.userapp.entity.User;
import com.example.userapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController combines @Controller and @ResponseBody, meaning this class handles
// web requests and its methods return directly bindable responses (e.g., JSON/XML).
@RestController
// @RequestMapping sets the base URL path for all endpoints in this controller.
@RequestMapping("/users")
public class UserController {

    // @Autowired injects an instance of UserService into this controller.
    @Autowired
    private UserService userService;

    /**
     * Handles GET requests to retrieve a user by ID.
     * Example URL: /users/1
     *
     * @param id The ID of the user, extracted from the URL path.
     * @return A ResponseEntity containing the User object if found, or an appropriate status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        // Call the service to get the user.
        User user = userService.getUserById(id);

        // If user is found, return 200 OK with the user object in the body.
        // If user is not found, userService.getUserById(id) returns null.
        // In this specific implementation, ResponseEntity.ok(null) would still return 200 OK with an empty body.
        // For a more robust API, you might return ResponseEntity.notFound().build() here.
        return ResponseEntity.ok(user);
    }
}
