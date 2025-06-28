package com.example.performance_test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.Duration;

public class PerformanceTesterTest {

    @Test
    public void testTaskCompletesWithinTimeLimit() {
        PerformanceTester tester = new PerformanceTester();

        // Assert the task completes within 500 ms
        assertTimeout(Duration.ofMillis(500), tester::performTask,
            "Task should complete within 500 milliseconds");
    }

    @Test
    public void testTaskFailsIfExceedsTimeout() {
        PerformanceTester tester = new PerformanceTester();

        // Assert the task fails if over 100 ms (this should fail)
        assertTimeoutPreemptively(Duration.ofMillis(100), tester::performTask,
            "Task should be forcefully stopped if over 100 milliseconds");
    }
}
