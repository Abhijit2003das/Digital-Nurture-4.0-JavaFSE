package com.example.interaction_order_demo;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class UserNotifierTest {

    @Test
    public void testMethodCallOrder() {
        NotificationService mockService = mock(NotificationService.class);

        UserNotifier notifier = new UserNotifier(mockService);
        notifier.notifyUser("karthiga@example.com");
        InOrder inOrder = inOrder(mockService);
        inOrder.verify(mockService).sendEmail("karthiga@example.com");
        inOrder.verify(mockService).sendSMS("karthiga@example.com");
    }
}

