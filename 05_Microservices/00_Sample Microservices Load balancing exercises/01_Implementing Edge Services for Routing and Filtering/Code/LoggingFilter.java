package com.example.edgeservice.filter; // Recommended package for filters

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Custom Global Filter for logging incoming requests to the Spring Cloud Gateway.
 * A {@code GlobalFilter} is applied to all routes configured in the Gateway.
 * This filter demonstrates how to intercept requests before they are routed
 * to their destination.
 */
@Component // Marks this class as a Spring component, allowing Spring to automatically detect and manage it.
public class LoggingFilter implements GlobalFilter {

    /**
     * This method is the core of the filter logic and is invoked for every request
     * that passes through the Gateway.
     *
     * @param exchange The current server web exchange, which provides access to the
     * HTTP request and response, as well as attributes.
     * @param chain    The filter chain, which allows the filter to delegate to the
     * next filter in the chain or to the route handler.
     * @return A {@code Mono<Void>} that signals when the filtering process is complete.
     * The {@code Mono} represents the asynchronous nature of WebFlux.
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Log the full URI of the incoming request.
        // This line will print to the console when a request hits the Gateway.
        System.out.println("Request received by Gateway: " + exchange.getRequest().getURI());

        // Continue the filter chain.
        // It's crucial to call chain.filter(exchange) to ensure the request proceeds
        // to the next filter or, ultimately, to the target service defined by the route.
        // If this line is omitted, the request will be stopped here.
        return chain.filter(exchange);
    }
}
