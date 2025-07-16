package com.example.oauth2centralizedauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer; // Import for Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configures the security filter chain for the application.
     * This method defines authorization rules and enables OAuth2 login.
     *
     * @param http The HttpSecurity object to configure.
     * @return A SecurityFilterChain instance.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Configure authorization for HTTP requests.
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    // All requests must be authenticated.
                    // If a user is not authenticated, they will be redirected to the OAuth2 login flow.
                    .anyRequest().authenticated()
            )
            // Enable OAuth2 Login.
            // Customizer.withDefaults() applies the default OAuth2 login configuration,
            // which includes generating a login page with configured providers.
            .oauth2Login(Customizer.withDefaults());

            // Optional: You can customize the OAuth2 login behavior further, for example:
            // .oauth2Login(oauth2LoginConfigurer -> {
            //     oauth2LoginConfigurer
            //         .loginPage("/custom-login") // Specify a custom login page URL
            //         .defaultSuccessUrl("/dashboard", true); // Redirect to /dashboard after successful login
            // });

        // Build and return the configured SecurityFilterChain.
        return http.build();
    }
}
