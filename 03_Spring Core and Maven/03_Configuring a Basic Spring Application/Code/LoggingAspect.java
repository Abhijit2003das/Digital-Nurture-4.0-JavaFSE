package com.library.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect // Marks this class as an Aspect
public class LoggingAspect {

    // Define a pointcut that matches all public methods within the com.library.service package
    // You can make this more specific if needed, e.g., only for BookService methods
    @Pointcut("execution(public * com.library.service.*.*(..))")
    public void serviceMethods() {}

    // Define an Around advice for the serviceMethods pointcut
    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.nanoTime(); // Get start time in nanoseconds

        System.out.println("AOP Log: Before executing method: " + joinPoint.getSignature().toShortString());

        Object result = joinPoint.proceed(); // Proceed with the actual method execution

        long endTime = System.nanoTime(); // Get end time
        long duration = (endTime - startTime) / 1_000_000; // Convert to milliseconds

        System.out.println("AOP Log: Method " + joinPoint.getSignature().toShortString() + " executed in " + duration + " ms");

        return result; // Return the result of the method
    }
}
