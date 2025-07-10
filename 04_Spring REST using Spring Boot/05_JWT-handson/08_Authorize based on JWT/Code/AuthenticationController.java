package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    // This SECRET_KEY MUST BE IDENTICAL to the one in JwtTokenUtil and JwtAuthorizationFilter
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor("a_very_long_and_secure_secret_key_for_jjwt_hs256".getBytes(StandardCharsets.UTF_8));

    @GetMapping("/authenticate")
    public Map<String, String> authenticate(@RequestHeader("Authorization") String authHeader) {
        LOGGER.info("Start authenticate()");
        LOGGER.debug("Authorization Header: {}", authHeader);

        String username = getUser(authHeader);
        LOGGER.debug("Decoded Username: {}", username);

        String generatedToken = generateJwt(username);
        LOGGER.debug("Generated JWT Token: {}", generatedToken);

        Map<String, String> response = new HashMap<>();
        response.put("token", generatedToken);

        LOGGER.info("End authenticate()");
        return response;
    }

    private String getUser(String authHeader) {
        LOGGER.debug("Start getUser() with authHeader: {}", authHeader);
        String encodedCredentials = authHeader.substring("Basic ".length());
        byte[] decodedBytes = Base64.getDecoder().decode(encodedCredentials);
        String decodedCredentials = new String(decodedBytes, StandardCharsets.UTF_8);
        String username = decodedCredentials.substring(0, decodedCredentials.indexOf(":"));
        LOGGER.debug("End getUser()");
        return username;
    }

    private String generateJwt(String user) {
        LOGGER.info("Start generateJwt() for user: {}", user);

        String token = Jwts.builder()
                .setSubject(user)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1200000)) // 20 minutes
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();

        LOGGER.debug("Generated JWT Token: {}", token);
        LOGGER.info("End generateJwt()");
        return token;
    }
}