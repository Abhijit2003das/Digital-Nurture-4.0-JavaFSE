package com.example.usertest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for UserController using MockMvc.
 * This class uses @WebMvcTest to focus solely on the web layer,
 * without starting a full Spring application context.
 */
@WebMvcTest(UserController.class) // Focuses on testing UserController and automatically configures MockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // Injected by @WebMvcTest, used to perform HTTP requests

    @MockBean // Creates a Mockito mock for UserService and adds it to the Spring application context
    private UserService userService; // This mock will be injected into UserController

    /**
     * Test case for retrieving a user by ID when the user exists.
     * We mock the behavior of UserService.getUserById() to return a specific user.
     */
    @Test
    void testGetUser_UserFound() throws Exception {
        // Given: A mock user object
        Long userId = 1L;
        User mockUser = new User(userId, "Jane Doe");

        // When: userService.getUserById(userId) is called, return the mock user
        when(userService.getUserById(userId)).thenReturn(mockUser);

        // Then: Perform a GET request to /users/{userId} and assert the response
        mockMvc.perform(get("/users/{id}", userId)) // Perform GET request to the specified URL
               .andExpect(status().isOk()) // Expect HTTP 200 OK status
               .andExpect(jsonPath("$.id").value(userId)) // Expect JSON response to have 'id' field matching userId
               .andExpect(jsonPath("$.name").value("Jane Doe")); // Expect JSON response to have 'name' field matching "Jane Doe"
    }

    /**
     * Test case for retrieving a user by ID when the user does not exist.
     * We mock the behavior of UserService.getUserById() to return null.
     */
    @Test
    void testGetUser_UserNotFound() throws Exception {
        // Given: A user ID that does not exist
        Long userId = 99L;

        // When: userService.getUserById(userId) is called, return null
        when(userService.getUserById(userId)).thenReturn(null);

        // Then: Perform a GET request to /users/{userId} and assert the response
        mockMvc.perform(get("/users/{id}", userId)) // Perform GET request to the specified URL
               .andExpect(status().isNotFound()); // Expect HTTP 404 Not Found status
    }
}
