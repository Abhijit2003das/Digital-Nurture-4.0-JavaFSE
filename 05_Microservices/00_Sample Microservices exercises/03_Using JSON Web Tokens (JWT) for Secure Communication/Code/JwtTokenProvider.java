package com.example.jwtauthdemo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import com.example.jwtauthdemo.config.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct; // Correct import for PostConstruct in Spring Boot 3+
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component // Marks this class as a Spring component, making it discoverable for dependency injection.
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;
    private Key key; // The secret key used for signing and verifying tokens

    // Constants for token validity and claims
    private static final long VALIDITY_IN_MILLISECONDS = 3600000; // 1 hour
    private static final String AUTHORITIES_KEY = "auth"; // Key to store authorities in JWT claims

    @Autowired
    public JwtTokenProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * Initializes the secret key from JwtConfig after dependency injection.
     * Uses Keys.hmacShaKeyFor to generate a secure key from the secret string.
     */
    @PostConstruct
    public void init() {
        // Ensure the secret key is long enough for HS256 (at least 32 bytes/characters)
        if (jwtConfig.getSecret().length() < 32) {
            System.err.println("WARNING: JWT secret key is too short for HS256. It should be at least 32 characters.");
            // In a production app, you might throw an exception or generate a random key here.
        }
        this.key = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

    /**
     * Creates a JWT token for a given username and roles.
     *
     * @param username The subject of the token (e.g., user's identifier).
     * @param roles A collection of GrantedAuthority objects representing user roles/authorities.
     * @return The generated JWT as a String.
     */
    public String createToken(String username, Collection<? extends GrantedAuthority> roles) {
        // Build claims (payload) for the JWT
        Claims claims = Jwts.claims().setSubject(username);

        // Convert roles to a comma-separated string to store in claims
        String authorities = roles.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        claims.put(AUTHORITIES_KEY, authorities); // Add authorities to claims

        Date now = new Date();
        Date validity = new Date(now.getTime() + VALIDITY_IN_MILLISECONDS); // Token expiration time

        // Build and sign the JWT
        return Jwts.builder()
                .setClaims(claims) // Set the claims
                .setIssuedAt(now) // Set the issue date
                .setExpiration(validity) // Set the expiration date
                .signWith(key, SignatureAlgorithm.HS256) // Sign with the secret key using HS256 algorithm
                .compact(); // Compact the JWT into its final string form
    }

    /**
     * Validates a given JWT token.
     *
     * @param token The JWT string to validate.
     * @return true if the token is valid, false otherwise.
     */
    public boolean validateToken(String token) {
        try {
            // Parse and validate the token using the secret key.
            // If the token is invalid (e.g., expired, tampered, wrong signature),
            // a JwtException will be thrown.
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            System.err.println("Invalid JWT signature or malformed token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("Expired JWT token: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Unsupported JWT token: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT token compact of handler are invalid: " + e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves authentication information from a JWT token.
     *
     * @param token The JWT string.
     * @return An Authentication object containing user details and authorities.
     */
    public Authentication getAuthentication(String token) {
        // Parse the token to get its claims (payload)
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        // Extract username from claims
        String username = claims.getSubject();

        // Extract authorities (roles) from claims and convert them to GrantedAuthority objects
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // Create a Spring Security User object
        User principal = new User(username, "", authorities); // Password is not needed here as it's already authenticated

        // Return a UsernamePasswordAuthenticationToken, which represents the authenticated user
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }
}
