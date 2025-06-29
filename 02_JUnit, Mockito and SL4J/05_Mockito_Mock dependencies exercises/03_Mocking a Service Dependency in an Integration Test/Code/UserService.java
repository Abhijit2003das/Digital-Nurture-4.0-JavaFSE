package com.example.userapp.service;

import com.example.userapp.entity.User;
import com.example.userapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// @Service marks this class as a Spring service component.
// Spring will detect and manage this class as a business logic component.
@Service
public class UserService {

    // @Autowired injects an instance of UserRepository into this service.
    // Spring automatically finds and provides a suitable bean.
    @Autowired
    private UserRepository userRepository;

    /**
     * Retrieves a User by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The User object if found, otherwise null.
     */
    public User getUserById(Long id) {
        // userRepository.findById(id) returns an Optional<User>.
        // .orElse(null) retrieves the User if present, otherwise returns null.
        return userRepository.findById(id).orElse(null);
    }
}
