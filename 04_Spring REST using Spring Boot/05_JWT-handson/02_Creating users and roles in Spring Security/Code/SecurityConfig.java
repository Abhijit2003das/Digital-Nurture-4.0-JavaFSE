package com.cognizant.springlearn.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        LOGGER.info("Start userDetailsService()");
        // Create in-memory users
        UserDetails adminUser = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("pwd"))
            .roles("ADMIN")
            .build();

        UserDetails regularUser = User.builder()
            .username("user")
            .password(passwordEncoder.encode("pwd"))
            .roles("USER")
            .build();

        LOGGER.info("End userDetailsService()");
        return new InMemoryUserDetailsManager(adminUser, regularUser);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        LOGGER.info("Start passwordEncoder()");
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        LOGGER.info("Start securityFilterChain()");
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF for simpler REST API testing (for now)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/countries").hasRole("USER") // Only users with 'USER' role can access /countries
                .anyRequest().authenticated() // Any other request must be authenticated (default)
            )
            .httpBasic(); // Enable HTTP Basic authentication
        LOGGER.info("End securityFilterChain()");
        return http.build();
    }
}