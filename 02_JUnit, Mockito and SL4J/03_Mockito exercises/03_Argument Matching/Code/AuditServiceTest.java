package com.example.argument_matching_demo;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class AuditServiceTest {

    @Test
    public void testArgumentMatching() {
    
        Logger mockLogger = mock(Logger.class);
        AuditService auditService = new AuditService(mockLogger);
        auditService.auditLogin("admin");
        verify(mockLogger).log(eq("INFO"), contains("admin"));
    }
}
