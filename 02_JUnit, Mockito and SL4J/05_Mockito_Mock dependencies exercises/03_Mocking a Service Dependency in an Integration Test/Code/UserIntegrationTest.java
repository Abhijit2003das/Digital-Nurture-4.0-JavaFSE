package com.example.userapp;

import com.example.userapp.controller.UserController; // Import UserController
import com.example.userapp.entity.User; // Import User entity
import com.example.userapp.repository.UserRepository; // Import UserRepository
import com.example.userapp.service.UserService; // Import UserService

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// This annotation loads the full application context for integration testing.
@SpringBootTest(classes = {SpringBootUserAppApplication.class, UserController.class, UserService.class, UserRepository.class})
// This annotation automatically configures MockMvc, which is used to perform HTTP requests in tests.
@AutoConfigureMockMvc
public class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc; // Injects MockMvc to simulate HTTP requests.

    @Autowired
    private ObjectMapper objectMapper; // Injects ObjectMapper for JSON serialization/deserialization.

    // @MockBean replaces the actual UserRepository bean in the application context with a Mockito mock.
    // This allows us to control its behavior during the test without needing a real database.
    @MockBean
    private UserRepository userRepository;

    private User testUser; // A test user object to be used in setup.

    @BeforeEach
    void setUp() {
        // Initialize a sample User object before each test.
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John Doe");
    }

    @Test
    void testGetUserById_Success() throws Exception {
        // Configure the mocked userRepository to return the testUser when findById(1L) is called.
        // This simulates a successful retrieval from the database.
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        // Perform a GET request to /users/1
        mockMvc.perform(get("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)) // Set the content type of the request.
                // Expect the HTTP status to be OK (200).
                .andExpect(status().isOk())
                // Expect the 'id' field in the JSON response to be 1.
                .andExpect(jsonPath("$.id").value(1L))
                // Expect the 'name' field in the JSON response to be "John Doe".
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        // Configure the mocked userRepository to return an empty Optional when findById(99L) is called.
        // This simulates a user not found scenario.
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Perform a GET request to /users/99
        mockMvc.perform(get("/users/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON))
                // Expect the HTTP status to be OK (200), as the controller returns null and MockMvc allows it.
                // Depending on the desired behavior, this could also be HttpStatus.NOT_FOUND if the controller
                // was designed to return a 404 for null users. For this example, it returns 200 with a null body.
                .andExpect(status().isOk())
                // Expect the response body to be empty, as the controller returns null for not found users.
                // This checks that the response is effectively a null JSON object.
                .andExpect(jsonPath("$").doesNotExist());
    }
}
