package com.example.jwtauthdemo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain; // Correct import for Servlet API in Spring Boot 3+
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component // Marks this class as a Spring component.
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired // Injects the JwtTokenProvider
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Filters incoming HTTP requests to extract and validate JWT tokens.
     * If a valid token is found, it sets the authentication in the SecurityContext.
     *
     * @param request The HttpServletRequest.
     * @param response The HttpServletResponse.
     * @param filterChain The FilterChain to proceed with.
     * @throws ServletException if a servlet-specific error occurs.
     * @throws IOException if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Resolve the token from the Authorization header
        String token = resolveToken(request);

        // 2. Validate the token and set authentication if valid
        // Also check if authentication is not already set for the current request
        // (e.g., by another filter or if it's a public endpoint that doesn't need JWT)
        if (token != null && jwtTokenProvider.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            Authentication auth = jwtTokenProvider.getAuthentication(token);
            // Set the authentication object in the SecurityContextHolder.
            // This makes the authenticated user available throughout the application for the current request.
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        // 3. Continue the filter chain
        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authorization header.
     * Expects the header in the format "Bearer <token>".
     *
     * @param request The HttpServletRequest.
     * @return The extracted token string, or null if not found or not in "Bearer" format.
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }
}
