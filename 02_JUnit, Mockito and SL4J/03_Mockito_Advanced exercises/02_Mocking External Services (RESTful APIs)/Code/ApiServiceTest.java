package com.example.mock_api_service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ApiServiceTest {

    @Test
    public void testServiceWithMockRestClient() {
        // Create mock
        RestClient mockRestClient = mock(RestClient.class);

        // Stub method
        when(mockRestClient.getResponse()).thenReturn("Mock Response");

        // Inject mock into service
        ApiService apiService = new ApiService(mockRestClient);

        // Test result
        String result = apiService.fetchData();
        assertEquals("Fetched Mock Response", result);
    }
}
