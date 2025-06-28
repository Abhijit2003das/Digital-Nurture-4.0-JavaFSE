package com.example.void_method_mock;

public class UserService {
	private Notifier notifier;

    public UserService(Notifier notifier) {
        this.notifier = notifier;
    }

    public void registerUser(String user) {
        notifier.sendNotification(user);
    }
}
