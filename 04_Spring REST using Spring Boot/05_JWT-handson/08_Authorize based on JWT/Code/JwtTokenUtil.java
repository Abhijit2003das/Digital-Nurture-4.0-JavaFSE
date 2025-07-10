package com.cognizant.springlearn.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys; // Import for Keys
import io.jsonwebtoken.JwtException; // Import for general JWT exceptions

import org.springframework.beans.factory.annotation.Value; // Keep if you want to load from properties
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey; // Import for SecretKey
import java.io.Serializable;
import java.nio.charset.StandardCharsets; // Import for StandardCharsets
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component // Keep this annotation if it's meant to be a Spring bean
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 hours in seconds

    // IMPORTANT: This SECRET_KEY MUST BE IDENTICAL to the one in AuthenticationController
    // and JwtAuthorizationFilter.
    // If you prefer to load from application.properties, you'd use @Value("${jwt.secret}")
    // and convert it to SecretKey. For now, we'll hardcode for consistency.
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("a_very_long_and_secure_secret_key_for_jjwt_hs256".getBytes(StandardCharsets.UTF_8));

    // If you were loading from application.properties, you'd convert it like this in a @PostConstruct
    // @Value("${jwt.secret}")
    // private String secretString;
    // private SecretKey secretKeyFromProperty;
    // @PostConstruct
    // public void init() {
    //     secretKeyFromProperty = Keys.hmacShaKeyFor(secretString.getBytes(StandardCharsets.UTF_8));
    // }


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        // This is the method where the SignatureException was occurring.
        // Ensure it uses the correct SECRET_KEY for parsing.
        return Jwts.parserBuilder() // Use parserBuilder() for JJWT 0.11.x+
                .setSigningKey(SECRET_KEY) // Use the consistent SECRET_KEY
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // This method is for generating tokens, if you choose to generate them here
    // instead of directly in AuthenticationController.
    // Ensure it also uses the consistent SECRET_KEY.
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS512) // Use the consistent SECRET_KEY and algorithm
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (JwtException e) {
            // Log specific JWT validation failures if needed
            // LOGGER.warn("JWT validation failed for user {}: {}", userDetails.getUsername(), e.getMessage());
            return false;
        }
    }
}