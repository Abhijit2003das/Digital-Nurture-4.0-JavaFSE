package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired; // For autowiring KeyResolver

// Import your custom CacheGatewayFilterFactory from its specific package
import com.example.apigateway.filter.CacheGatewayFilterFactory;

@SpringBootApplication
public class ApiGatewayApplication {

    // Autowire your custom KeyResolver bean
    @Autowired
    private HostAddrKeyResolver hostAddrKeyResolver;

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    /**
     * Defines the RedisRateLimiter bean.
     * This explicitly sets the replenishRate (tokens added per second)
     * and burstCapacity (max tokens bucket can hold) for the rate limiter.
     * Spring Cloud Gateway will use this bean for the RequestRateLimiter filter.
     */
    @Bean
    public RedisRateLimiter redisRateLimiter() {
        // replenishRate: 1 request per second
        // burstCapacity: allows up to 3 requests in a burst
        return new RedisRateLimiter(1, 3);
    }

    /**
     * Defines the custom routes and applies filters programmatically.
     * This method replaces the route definitions previously in application.properties.
     *
     * @param builder RouteLocatorBuilder for defining routes.
     * @param rateLimiterFactory Automatically injected factory for RequestRateLimiter.
     * @return Configured RouteLocator.
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           RequestRateLimiterGatewayFilterFactory rateLimiterFactory) {
        return builder.routes()
            // Route for Customer Service
            .route("customer_service_route", r -> r.path("/customer-api/**")
                .filters(f -> f.stripPrefix(1) // Strips "/customer-api" from the path
                    // Apply RequestRateLimiter filter
                    .filter(rateLimiterFactory.apply(new RequestRateLimiterGatewayFilterFactory.Config()
                        .setKeyResolver(hostAddrKeyResolver) // Use the custom KeyResolver bean
                        .setRateLimiter(redisRateLimiter())   // Use the custom RedisRateLimiter bean
                    ))
                    // Apply your custom CacheGatewayFilterFactory
                    // Instantiate CacheGatewayFilterFactory and apply its default config
                    .filter(new CacheGatewayFilterFactory().apply(new CacheGatewayFilterFactory.Config()))
                )
                .uri("http://localhost:8081")) // Target URI for Customer Service

            // Route for Billing Service
            .route("billing_service_route", r -> r.path("/billing-api/**")
                .filters(f -> f.stripPrefix(1) // Strips "/billing-api" from the path
                    // Apply RequestRateLimiter filter
                    .filter(rateLimiterFactory.apply(new RequestRateLimiterGatewayFilterFactory.Config()
                        .setKeyResolver(hostAddrKeyResolver)
                        .setRateLimiter(redisRateLimiter())
                    ))
                    // Apply your custom CacheGatewayFilterFactory
                    .filter(new CacheGatewayFilterFactory().apply(new CacheGatewayFilterFactory.Config()))
                )
                .uri("http://localhost:8082")) // Target URI for Billing Service
            .build(); // Build the RouteLocator
    }
}