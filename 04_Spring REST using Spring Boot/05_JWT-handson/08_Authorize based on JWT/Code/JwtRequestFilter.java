package com.cognizant.springlearn.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// IMPORTANT: This filter is still active in your logs.
// If you intend to use JwtAuthorizationFilter (which extends BasicAuthenticationFilter)
// as your primary JWT validation filter, you might want to review your SecurityConfig
// to ensure this filter is not redundantly added or is properly ordered.
// For now, we're ensuring it works by fixing its dependency (JwtTokenUtil).

@Component // Keep @Component if it's meant to be a Spring bean
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil; // This dependency is crucial

    // Constructor injection for its dependencies
    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                LOGGER.debug("JWT Token extracted for user: {}", username);
            } catch (io.jsonwebtoken.ExpiredJwtException e) { // Specific exception for expired token
                LOGGER.warn("JWT Token has expired for user {}: {}", e.getClaims().getSubject(), e.getMessage());
            } catch (io.jsonwebtoken.SignatureException e) { // Specific exception for signature mismatch
                LOGGER.error("JWT Signature validation failed: {}", e.getMessage());
            } catch (io.jsonwebtoken.MalformedJwtException e) { // Specific exception for malformed token
                LOGGER.error("JWT Malformed: {}", e.getMessage());
            } catch (io.jsonwebtoken.UnsupportedJwtException e) { // Specific exception for unsupported JWT
                LOGGER.error("Unsupported JWT: {}", e.getMessage());
            } catch (IllegalArgumentException e) {
                LOGGER.error("Unable to get JWT Token: {}", e.getMessage());
            } catch (Exception e) { // Catch any other unexpected errors during token parsing
                LOGGER.error("An unexpected error occurred during JWT token parsing: {}", e.getMessage(), e);
            }
        } else {
            LOGGER.debug("JWT Token does not begin with Bearer String or is missing for request: {}", request.getRequestURI());
        }

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            // if token is valid configure Spring Security to manually set authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the Context, we specify that the current user is authenticated.
                // So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                LOGGER.info("User {} authenticated successfully via JwtRequestFilter.", username);
            } else {
                LOGGER.warn("JWT Token validation failed (validateToken method returned false) for user: {}", username);
            }
        }
        chain.doFilter(request, response);
    }
}