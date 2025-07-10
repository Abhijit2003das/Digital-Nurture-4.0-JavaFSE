package com.cognizant.springlearn.security;

import com.cognizant.springlearn.jwt.JwtUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SecurityException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    // This SECRET_KEY MUST BE IDENTICAL to the one in AuthenticationController and JwtTokenUtil
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("a_very_long_and_secure_secret_key_for_jjwt_hs256".getBytes(StandardCharsets.UTF_8));

    private final JwtUserDetailsService jwtUserDetailsService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtUserDetailsService jwtUserDetailsService) {
        super(authenticationManager);
        this.jwtUserDetailsService = jwtUserDetailsService;
        LOGGER.info("Start JwtAuthorizationFilter constructor");
        LOGGER.debug("AuthenticationManager: {}", authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        LOGGER.info("Start doFilterInternal in JwtAuthorizationFilter");
        String header = req.getHeader("Authorization");
        LOGGER.debug("Authorization Header received: {}", header);

        if (header == null || !header.startsWith("Bearer ")) {
            LOGGER.debug("No Bearer token found or incorrect format. Proceeding with filter chain.");
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = null;
        try {
            authentication = getAuthentication(req);
        } catch (Exception e) {
            LOGGER.error("Error during JWT authentication processing: {}", e.getMessage(), e);
        }

        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.debug("Authentication successful for user: {}", authentication.getName());
        } else {
            LOGGER.warn("JWT authentication failed. No authentication object set in SecurityContextHolder.");
        }

        chain.doFilter(req, res);
        LOGGER.info("End doFilterInternal in JwtAuthorizationFilter");
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        LOGGER.info("Start getAuthentication (JWT parsing)");
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            String jwt = token.replace("Bearer ", "");
            LOGGER.debug("Extracted raw JWT for parsing: {}", jwt);

            Jws<Claims> jws;
            try {
                jws = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseClaimsJws(jwt);

                String username = jws.getBody().getSubject();
                LOGGER.debug("Username (subject) extracted from JWT: {}", username);

                if (username != null && !username.trim().isEmpty()) {
                    LOGGER.debug("Attempting to load UserDetails for username: {}", username);
                    UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

                    if (userDetails != null) {
                        LOGGER.debug("UserDetails successfully loaded for {}. Authorities: {}", username, userDetails.getAuthorities());
                        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    } else {
                        LOGGER.warn("JwtUserDetailsService returned null for username: {}. User not found.", username);
                        return null;
                    }
                } else {
                    LOGGER.warn("JWT subject (username) was null or empty.");
                    return null;
                }
            } catch (SignatureException e) {
                LOGGER.error("JWT Signature Exception: Token signature does not match or is corrupted. Message: {}", e.getMessage());
                return null;
            } catch (ExpiredJwtException e) {
                LOGGER.error("JWT Expired Exception: Token has expired. Message: {}", e.getMessage());
                return null;
            } catch (MalformedJwtException e) {
                LOGGER.error("JWT Malformed Exception: Token is not a valid JWT format. Message: {}", e.getMessage());
                return null;
            } catch (UnsupportedJwtException e) {
                LOGGER.error("JWT Unsupported Exception: JWT in a format not supported. Message: {}", e.getMessage());
                return null;
            } catch (SecurityException e) {
                LOGGER.error("JWT Security Exception: A security issue occurred during parsing. Message: {}", e.getMessage());
                return null;
            } catch (JwtException ex) {
                LOGGER.error("Generic JWT Parsing Error: {}. Message: {}", ex.getClass().getName(), ex.getMessage());
                return null;
            } catch (Exception e) {
                LOGGER.error("Unexpected error during UserDetails loading in JWT filter: {}. Message: {}", e.getClass().getName(), e.getMessage(), e);
                return null;
            }
        }
        LOGGER.info("End getAuthentication - No valid token found in header.");
        return null;
    }
}