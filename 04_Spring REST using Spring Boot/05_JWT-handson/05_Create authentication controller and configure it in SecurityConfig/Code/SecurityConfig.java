package com.cognizant.springlearn.security;

import com.cognizant.springlearn.jwt.JwtUserDetailsService; // Still needed for user details service

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
// REMOVED: import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; // No longer adding custom JWT filter

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    private final JwtUserDetailsService jwtUserDetailsService;
    // REMOVED: private final JwtTokenUtil jwtTokenUtil; // No longer needed in this config
    private final PasswordEncoder passwordEncoder; // Still needed for DaoAuthenticationProvider

    // Constructor injection for JwtUserDetailsService and PasswordEncoder
    public SecurityConfig(JwtUserDetailsService jwtUserDetailsService, PasswordEncoder passwordEncoder) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        LOGGER.info("Configuring AuthenticationManager bean.");
        return authenticationConfiguration.getAuthenticationManager();
    }

    // DaoAuthenticationProvider now uses the injected fields
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        LOGGER.info("Configuring DaoAuthenticationProvider bean.");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jwtUserDetailsService); // Uses injected field
        authProvider.setPasswordEncoder(passwordEncoder); // Uses injected field
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // REMOVED: JwtRequestFilter instantiation // No longer needed

        LOGGER.info("Configuring SecurityFilterChain.");
        http
            .csrf(csrf -> csrf.disable()) // Keep CSRF disabled for simpler API testing
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/").permitAll() // Allow root path for browser access
                .requestMatchers("/countries").hasRole("USER") // /countries accessible only to 'USER' role
                .requestMatchers("/authenticate").hasAnyRole("USER", "ADMIN") // /authenticate accessible to 'USER' or 'ADMIN' roles
                .anyRequest().authenticated() // All other requests require any authentication
            )
            .httpBasic() // Enable HTTP Basic authentication
            .and() // Needed to chain back after httpBasic()
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Keep stateless for API
            )
            .authenticationProvider(authenticationProvider()); // Use the authenticationProvider bean method.

        // REMOVED: .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // No longer adding JWT filter

        LOGGER.info("SecurityFilterChain configured successfully.");
        return http.build();
    }
}