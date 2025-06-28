package com.example.void_exception_demo;

public class Application {
	private LoggerService logger;

    public Application(LoggerService logger) {
        this.logger = logger;
    }

    public void run() {
        logger.log("Starting app...");
    }
}
