package com.example.jwtauthdemo.payload;

// Simple POJO (Plain Old Java Object) to represent the login request body.
// It will be used by Spring to automatically map JSON request bodies.
public class LoginRequest {
    private String username;
    private String password;

    // Getters and Setters are required for Spring to bind request parameters.
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
