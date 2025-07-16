package com.example.apigateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Configuration class for Resilience4j circuit breakers in Spring Cloud Gateway.
 * This allows programmatic customization of default circuit breaker and time limiter settings.
 */
@Configuration
public class ResilienceConfiguration {

    /**
     * Defines a customizer for the Resilience4jCircuitBreakerFactory.
     * This bean will apply default configurations to all circuit breakers
     * created by the factory, unless overridden by instance-specific properties
     * in application.properties.
     *
     * @return A Customizer for Resilience4jCircuitBreakerFactory.
     */
    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                // Configure default Circuit Breaker settings
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .failureRateThreshold(50) // Percentage of failures to open the circuit
                        .slidingWindowSize(10)    // Number of calls in the sliding window
                        .waitDurationInOpenState(Duration.ofSeconds(5)) // Time circuit stays open
                        .permittedNumberOfCallsInHalfOpenState(3) // Calls allowed in half-open state
                        .build())
                // Configure default Time Limiter settings (for timeout)
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(2)) // Default timeout for calls
                        .build())
                .build());
    }
}