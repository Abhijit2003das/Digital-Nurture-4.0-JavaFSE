package com.example.apigateway.filter; // Correct package declaration for this file

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus; // Use HttpStatus for status checks (implements HttpStatusCode)

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component // Mark as a Spring component so it's discoverable
public class CacheGatewayFilterFactory extends AbstractGatewayFilterFactory<CacheGatewayFilterFactory.Config> {

    private static final Logger log = LoggerFactory.getLogger(CacheGatewayFilterFactory.class);

    // Simple in-memory cache to store expiration times for paths that were successfully served.
    // This simulates caching by marking a path as "recently served successfully".
    // It does NOT store the actual response body.
    private final ConcurrentMap<String, Long> cacheExpirationTimes = new ConcurrentHashMap<>();
    private static final Duration DEFAULT_CACHE_DURATION = Duration.ofSeconds(10); // Default cache time (10 seconds)

    public CacheGatewayFilterFactory() {
        super(Config.class); // Specify the configuration class for this filter factory
    }

    /**
     * The core logic of the custom filter.
     * It checks if a request path is "cached" (recently successful).
     * If so, it adds an "X-Cache: HIT" header. Otherwise, "X-Cache: MISS".
     * It always forwards the request down the filter chain.
     * On successful response from downstream, it updates the cache expiration time.
     *
     * @param config The configuration for this filter instance.
     * @return A GatewayFilter instance.
     */
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            Long expirationTime = cacheExpirationTimes.get(path);

            // Check if the path is currently considered "cached" (i.e., its expiration time has not passed)
            if (expirationTime != null && System.currentTimeMillis() < expirationTime) {
                log.info("Cache HIT for: {}", path);
                exchange.getResponse().getHeaders().add("X-Cache", "HIT");
                // Since we are not caching the body, we still proceed with the request
                // to get the actual content from the downstream service.
                // The "HIT" header merely indicates our filter logic detected a recent success.
                return chain.filter(exchange);
            } else {
                log.info("Cache MISS for: {}", path);
                exchange.getResponse().getHeaders().add("X-Cache", "MISS");

                // Continue the filter chain to get the actual response from the downstream service.
                // After the downstream service responds, the Mono.fromRunnable will execute.
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    // This block executes AFTER the downstream service has processed the request
                    // and its response is available (before sending back to client).
                    HttpStatus status = (HttpStatus) exchange.getResponse().getStatusCode(); // Cast to HttpStatus
                    if (status != null && status.is2xxSuccessful()) {
                        log.debug("Caching successful response status for {}", path);
                        // Update the cache with a new expiration time for this path
                        cacheExpirationTimes.put(path, System.currentTimeMillis() + config.getDuration().toMillis());
                    } else {
                        log.debug("Not caching non-successful response status for {}", path);
                        // If the response was not successful, remove it from cache (if it was there)
                        cacheExpirationTimes.remove(path);
                    }
                }));
            }
        };
    }

    /**
     * Configuration properties for the CacheGatewayFilterFactory.
     * This inner class allows you to define properties that can be set
     * for each instance of this filter (e.g., via application.properties/yml,
     * though here we're using Java config directly).
     */
    public static class Config {
        private Duration duration = DEFAULT_CACHE_DURATION; // Default cache duration

        public Duration getDuration() {
            return duration;
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }
    }
}