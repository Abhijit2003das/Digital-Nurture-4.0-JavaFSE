package com.library.aspect;

import org.aspectj.lang.JoinPoint; // NEW: Import JoinPoint
import org.aspectj.lang.annotation.After; // NEW: Import @After
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before; // NEW: Import @Before
import org.aspectj.lang.annotation.Pointcut; // NEW: Import @Pointcut

@Aspect // Marks this class as an Aspect
public class LoggingAspect {

    // Define a pointcut that matches all public methods within the com.library.service package
    // This will target methods like BookService.getAvailableBooks()
    @Pointcut("execution(public * com.library.service.*.*(..))")
    public void serviceMethods() {}

    // @Before advice: Executes before the target method is called
    @Before("serviceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("AOP Log [BEFORE]: Entering method: " + joinPoint.getSignature().getName() +
                           " with arguments: " + java.util.Arrays.toString(joinPoint.getArgs()));
    }

    // @After advice: Executes after the target method completes (regardless of success or exception)
    @After("serviceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("AOP Log [AFTER]: Exiting method: " + joinPoint.getSignature().getName());
    }

    // You could also add @AfterReturning (for successful completion) and @AfterThrowing (for exceptions)
    // @AfterReturning(pointcut = "serviceMethods()", returning = "result")
    // public void logAfterReturning(JoinPoint joinPoint, Object result) {
    //     System.out.println("AOP Log [AFTER RETURNING]: Method " + joinPoint.getSignature().getName() + " returned: " + result);
    // }

    // @AfterThrowing(pointcut = "serviceMethods()", throwing = "error")
    // public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
    //     System.out.println("AOP Log [AFTER THROWING]: Method " + joinPoint.getSignature().getName() + " threw exception: " + error.getMessage());
    // }
}
