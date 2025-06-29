package com.example.userdemo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    public UserControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User mockUser = new User();
        mockUser.setId(userId);
        mockUser.setName("John Doe");

        when(userService.getUserById(userId)).thenReturn(mockUser);

        ResponseEntity<User> response = userController.getUser(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userId, response.getBody().getId());
        assertEquals("John Doe", response.getBody().getName());

        verify(userService, times(1)).getUserById(userId);
    }
}