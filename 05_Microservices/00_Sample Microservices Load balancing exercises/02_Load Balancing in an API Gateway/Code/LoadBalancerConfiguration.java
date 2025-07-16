package com.example.gatewayservice.config;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/**
 * Explicit Load Balancer Configuration for the 'example-service'.
 * This configuration directly provides a static list of service instances
 * for the 'example-service' to Spring Cloud LoadBalancer.
 *
 * This approach is used when property-based static configuration might not be
 * picked up correctly, or when you need more programmatic control over instance
 * discovery without a full discovery server (like Eureka).
 */
@Configuration
public class LoadBalancerConfiguration {

    /**
     * Defines a custom {@link ServiceInstanceListSupplier} bean for the "example-service".
     * This bean is responsible for providing the list of available service instances
     * to the load balancer.
     *
     * @return A {@link ServiceInstanceListSupplier} that emits a Flux containing
     * a static list of {@link ServiceInstance} objects for "example-service".
     */
    @Bean
    public ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new ServiceInstanceListSupplier() {
            @Override
            public String getServiceId() {
                // This must match the service ID used in the Gateway route URI (e.g., 'lb://example-service')
                return "example-service";
            }

            @Override
            public Flux<List<ServiceInstance>> get() {
                // Return a Flux that emits a static list of service instances.
                // We are creating two DefaultServiceInstance objects, one for each backend.
                // IMPORTANT: The constructor for DefaultServiceInstance now expects an 'instanceId' as the first argument.
                return Flux.just(Arrays.asList(
                        // instanceId, serviceId, host, port, secure (boolean)
                        new DefaultServiceInstance("backend-service-instance-1", "example-service", "localhost", 8081, false),
                        new DefaultServiceInstance("backend-service-instance-2", "example-service", "localhost", 8082, false)
                ));
            }
        };
    }
}
