package com.example.void_exception_demo;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApplicationTest {

    @Test
    public void testVoidMethodThrowsException() {
        LoggerService mockLogger = mock(LoggerService.class);

        doThrow(new RuntimeException("Logging failed"))
            .when(mockLogger).log("Starting app...");

        Application app = new Application(mockLogger);

        RuntimeException exception = assertThrows(RuntimeException.class, app::run);
        assertEquals("Logging failed", exception.getMessage());

        verify(mockLogger).log("Starting app...");
    }
}

