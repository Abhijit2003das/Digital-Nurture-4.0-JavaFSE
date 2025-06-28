package com.example.interaction_order_demo;

public class UserNotifier {
	 private NotificationService service;

	    public UserNotifier(NotificationService service) {
	        this.service = service;
	    }

	    public void notifyUser(String user) {
	        service.sendEmail(user);
	        service.sendSMS(user);
	    }
}
