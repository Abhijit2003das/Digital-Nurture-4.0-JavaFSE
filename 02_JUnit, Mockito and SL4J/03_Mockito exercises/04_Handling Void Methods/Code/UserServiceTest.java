package com.example.void_method_mock;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Test
    public void testVoidMethodInteraction() {
       Notifier mockNotifier = mock(Notifier.class);
       doNothing().when(mockNotifier).sendNotification("karthiga");
       UserService userService = new UserService(mockNotifier);
       userService.registerUser("karthiga");
       verify(mockNotifier).sendNotification("karthiga");
    }
}
