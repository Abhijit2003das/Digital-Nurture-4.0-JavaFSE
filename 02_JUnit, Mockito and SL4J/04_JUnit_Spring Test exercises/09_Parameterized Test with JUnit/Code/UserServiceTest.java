package com.example.usertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test; // Keep @Test for other tests if you add more
import org.junit.jupiter.params.ParameterizedTest; // Import ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource; // Import CsvSource
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test method.
        // This is still needed for parameterized tests as well.
        MockitoAnnotations.openMocks(this);
    }

    /**
     * This is a parameterized test for the getUserById method in UserService.
     * It uses @CsvSource to provide multiple sets of input data and expected outcomes.
     *
     * Test cases:
     * 1. User with ID 100 is found with name "Test User A".
     * 2. User with ID 200 is found with name "Test User B".
     * 3. User with ID 999 is NOT found (should throw UserNotFoundException).
     * 4. User with ID 888 is NOT found (should throw UserNotFoundException).
     *
     * @param id The user ID to test.
     * @param expectedName The expected name of the user if found (can be null for not-found cases).
     * @param shouldUserExist A boolean flag indicating if the user is expected to exist.
     */
    @ParameterizedTest // Marks this method as a parameterized test
    @CsvSource({ // Provides comma-separated values for each test iteration
        "100, Test User A, true",  // Test case: User found
        "200, Test User B, true",  // Test case: Another user found
        "999, , false",            // Test case: User not found (empty string for name)
        "888, , false"             // Test case: Another user not found
    })
    void testGetUserById_Parameterized(Long id, String expectedName, boolean shouldUserExist) {
        if (shouldUserExist) {
            // Scenario: User is expected to be found
            User mockUser = new User(id, expectedName);
            // Mock the repository to return the user
            when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));

            // Call the service method
            User foundUser = userService.getUserById(id);

            // Assertions for found user
            assertNotNull(foundUser, "User should be found for ID: " + id);
            assertEquals(id, foundUser.getId(), "User ID should match for ID: " + id);
            assertEquals(expectedName, foundUser.getName(), "User name should match for ID: " + id);

        } else {
            // Scenario: User is not expected to be found (should throw exception)
            // Mock the repository to return an empty Optional
            when(userRepository.findById(id)).thenReturn(Optional.empty());

            // Assert that calling userService.getUserById throws UserNotFoundException
            Exception exception = assertThrows(UserNotFoundException.class, () -> {
                userService.getUserById(id);
            }, "UserNotFoundException should be thrown for ID: " + id);

            // Verify the exception message
            String expectedMessagePart = "User not found with ID: " + id;
            assertTrue(exception.getMessage().contains(expectedMessagePart),
                       "Exception message should contain: '" + expectedMessagePart + "' for ID: " + id);
        }

        // Verify that userRepository.findById was called exactly once with the correct ID for all cases
        verify(userRepository, times(1)).findById(id);
        // Reset mocks after each parameterized invocation to ensure clean state
        reset(userRepository);
    }
    // You can remove the old specific tests if you are fully replacing them.
    // For clarity, I'm showing the parameterized one replacing the previous logic.

    // If you had other @Test methods in UserServiceTest, they would remain here.
    // For this example, we're assuming this new test replaces the previous two.
}
