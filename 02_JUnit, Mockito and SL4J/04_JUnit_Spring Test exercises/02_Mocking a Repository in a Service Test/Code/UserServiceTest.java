package com.example.usertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock // This will create a mock instance of UserRepository
    private UserRepository userRepository;

    @InjectMocks // This will inject the mocked UserRepository into UserService
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test method
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById_UserFound() {
        // Given: A user exists with a specific ID
        Long userId = 1L;
        User mockUser = new User(userId, "John Doe");

        // When: userRepository.findById is called with userId, return the mockUser
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        // Then: Call the service method
        User foundUser = userService.getUserById(userId);

        // Assertions
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals("John Doe", foundUser.getName());

        // Verify that userRepository.findById was called exactly once with the correct ID
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testGetUserById_UserNotFound() {
        // Given: No user exists with a specific ID
        Long userId = 2L;

        // When: userRepository.findById is called with userId, return an empty Optional
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Then: Call the service method
        User foundUser = userService.getUserById(userId);

        // Assertions
        assertNull(foundUser);

        // Verify that userRepository.findById was called exactly once with the correct ID
        verify(userRepository, times(1)).findById(userId);
    }
}