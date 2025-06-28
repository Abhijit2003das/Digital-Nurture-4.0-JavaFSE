package com.example.logback_appenders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiAppenderLogger {

    private static final Logger logger = LoggerFactory.getLogger(MultiAppenderLogger.class);

    public static void main(String[] args) {
        logger.debug("Debug message");
        logger.info("Info message");
        logger.warn("Warning message");
        logger.error("Error message");
    }
}
