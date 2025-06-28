package com.example.mock_demo;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ServiceTest {

    @Test
    public void testServiceWithMockRepository() {
        // Create mock repository
        Repository mockRepository = mock(Repository.class);

        // Stub method to return predefined data
        when(mockRepository.getData()).thenReturn("Mock Data");

        // Inject mock into service
        Service service = new Service(mockRepository);

        // Call method and assert result
        String result = service.processData();
        assertEquals("Processed Mock Data", result);
    }
}
