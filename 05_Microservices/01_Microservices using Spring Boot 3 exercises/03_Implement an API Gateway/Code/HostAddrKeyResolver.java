package com.example.apigateway;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.springframework.web.server.ServerWebExchange; // Explicitly import ServerWebExchange

/**
 * Custom KeyResolver that uses the client's host address (IP)
 * as the key for rate limiting.
 */
@Component // Mark as a Spring component so it can be autowired/referenced
public class HostAddrKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        // Retrieves the remote address of the client and returns its host IP as the key
        // This is suitable for direct client connections. For requests coming through
        // proxies/load balancers, you might need to extract from X-Forwarded-For header.
        if (exchange.getRequest().getRemoteAddress() != null) {
            return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
        }
        // Fallback if remote address is null (shouldn't happen in typical HTTP requests)
        return Mono.just("unknown");
    }
}