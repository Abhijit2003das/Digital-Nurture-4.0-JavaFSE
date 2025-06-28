package com.example.argument_matching_demo;

public class AuditService {
	private Logger logger;

    public AuditService(Logger logger) {
        this.logger = logger;
    }

    public void auditLogin(String user) {
        logger.log("INFO", "User " + user + " logged in");
    }
}
