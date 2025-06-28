package com.example.multi_return_mock;

public class MessageService {
	private ExternalApi api;

    public MessageService(ExternalApi api) {
        this.api = api;
    }

    public String[] fetchMessages() {
        return new String[] {
            api.getNextMessage(),
            api.getNextMessage(),
            api.getNextMessage()
        };
    }

}
