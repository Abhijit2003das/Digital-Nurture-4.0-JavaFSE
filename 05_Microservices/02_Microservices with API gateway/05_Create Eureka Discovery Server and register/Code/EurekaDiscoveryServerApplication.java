package com.cognizant.eureka_discovery_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer; // Make sure to import this

@SpringBootApplication
@EnableEurekaServer // Add this annotation
public class EurekaDiscoveryServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaDiscoveryServerApplication.class, args);
    }
}