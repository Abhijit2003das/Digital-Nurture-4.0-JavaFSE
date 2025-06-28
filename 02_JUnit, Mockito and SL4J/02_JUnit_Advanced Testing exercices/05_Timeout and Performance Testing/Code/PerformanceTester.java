package com.example.performance_test;

public class PerformanceTester {
	public void performTask() {
        try {
            // Simulate task delay (300 milliseconds)
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
