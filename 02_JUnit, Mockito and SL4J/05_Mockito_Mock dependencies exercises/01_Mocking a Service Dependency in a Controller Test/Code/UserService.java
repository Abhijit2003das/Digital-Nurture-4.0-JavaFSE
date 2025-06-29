package com.example.userdemo;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getUserById(Long id) {
        User user = new User();
        user.setId(id);
        user.setName("Dummy User");
        return user;
    }
}