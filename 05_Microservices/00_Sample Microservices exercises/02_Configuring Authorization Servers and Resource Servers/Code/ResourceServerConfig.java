package com.example.oauth2resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer; // Import for Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Marks this class as a source of bean definitions.
@EnableWebSecurity // Enables Spring Security's web security support and integrates with Spring MVC.
public class ResourceServerConfig {

    /**
     * Configures the security filter chain for the Resource Server.
     * This method defines authorization rules and enables JWT-based OAuth2 Resource Server.
     *
     * @param http The HttpSecurity object to configure.
     * @return A SecurityFilterChain instance, which defines the security rules.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Configure authorization for incoming HTTP requests.
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    // All requests to any endpoint must be authenticated.
                    // This means a valid JWT must be present in the 'Authorization: Bearer <token>' header.
                    .anyRequest().authenticated()
            )
            // Enable OAuth2 Resource Server support.
            // This tells Spring Security to expect and validate JWTs in the Authorization header.
            // Customizer.withDefaults() applies the default JWT validation configuration,
            // which uses the 'issuer-uri' from application.yml to fetch JWKS (public keys).
            .oauth2ResourceServer(oauth2ResourceServer ->
                oauth2ResourceServer
                    .jwt(Customizer.withDefaults()) // Configure JWT specific settings for token validation
            );

        // Build and return the configured SecurityFilterChain.
        return http.build();
    }
}
