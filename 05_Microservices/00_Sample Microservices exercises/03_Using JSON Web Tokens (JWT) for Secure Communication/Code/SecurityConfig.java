package com.example.jwtauthdemo.config;

import com.example.jwtauthdemo.security.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration // Marks this class as a source of bean definitions.
@EnableWebSecurity // Enables Spring Security's web security support.
public class SecurityConfig {

    @Autowired // Injects the custom JWT token filter.
    private JwtTokenFilter jwtTokenFilter;

    /**
     * Defines the security filter chain.
     * Configures CSRF, session management, authorization rules, and adds the JWT filter.
     *
     * @param http The HttpSecurity object to configure.
     * @return A SecurityFilterChain instance.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF (Cross-Site Request Forgery) protection.
            // This is common for stateless REST APIs using JWTs, as JWTs are not susceptible to CSRF attacks
            // in the same way session-based authentication is.
            .csrf(csrf -> csrf.disable())

            // Configure session management to be stateless.
            // This means no session will be created or used by Spring Security,
            // as authentication is handled by JWTs on each request.
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Configure authorization for HTTP requests.
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    // Allow unauthenticated access to the /auth/login endpoint.
                    // This is where users will send their credentials to get a JWT.
                    .requestMatchers("/auth/login").permitAll()
                    // All other requests require authentication.
                    .anyRequest().authenticated()
            )
            // Add the custom JwtTokenFilter before Spring Security's default
            // UsernamePasswordAuthenticationFilter. This ensures our JWT validation
            // happens early in the filter chain.
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        // Build and return the configured SecurityFilterChain.
        return http.build();
    }

    /**
     * Configures the PasswordEncoder bean.
     * Uses BCryptPasswordEncoder for strong password hashing.
     *
     * @return A PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures an in-memory UserDetailsService for demonstration purposes.
     * In a real application, this would be replaced with a service that loads users from a database.
     *
     * @param passwordEncoder The PasswordEncoder to encode user passwords.
     * @return A UserDetailsService instance.
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder.encode("password")) // Encode the password
            .roles("USER")
            .build();

        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("adminpass")) // Encode the password
            .roles("ADMIN", "USER")
            .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    /**
     * Configures the AuthenticationManager bean.
     * This manager is used to authenticate user credentials.
     *
     * @param userDetailsService The UserDetailsService to load user details.
     * @param passwordEncoder The PasswordEncoder to verify passwords.
     * @return An AuthenticationManager instance.
     */
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authenticationProvider);
    }
}
