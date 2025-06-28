package com.example.multi_return_mock;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MessageServiceTest {

    @Test
    public void testMultipleReturnsFromMock() {
        ExternalApi mockApi = mock(ExternalApi.class);

        when(mockApi.getNextMessage()).thenReturn("Hello", "Hi", "Hey");

        MessageService service = new MessageService(mockApi);
        String[] result = service.fetchMessages();

        assertEquals("Hello", result[0]);
        assertEquals("Hi", result[1]);
        assertEquals("Hey", result[2]);

        verify(mockApi, times(3)).getNextMessage();
    }
}
