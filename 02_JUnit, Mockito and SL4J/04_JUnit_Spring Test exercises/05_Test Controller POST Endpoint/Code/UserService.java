package com.example.usertest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional; // Keep this import as it's useful for findById's return type

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * Saves a user to the database.
     * If the user has an ID, it updates an existing user.
     * If the user has no ID, it creates a new user.
     *
     * @param user The user object to be saved.
     * @return The saved user object, potentially with a generated ID.
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
