package com.example.jwtauthdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration // Marks this class as a source of bean definitions.
public class JwtConfig {

    // Injects the value of 'spring.security.jwt.secret' from application.yml
    @Value("${spring.security.jwt.secret}")
    private String secret;

    /**
     * Returns the JWT secret key.
     * @return The secret key as a String.
     */
    public String getSecret() {
        return secret;
    }
}
