package com.example.oauth2resourceserver.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Marks this class as a REST controller, meaning its methods return data directly (e.g., JSON, String).
public class SecureController {

    /**
     * A simple secure endpoint that requires a valid JWT access token for access.
     * If a valid JWT is provided in the Authorization header, this endpoint will return a success message.
     * Otherwise, Spring Security will return a 401 Unauthorized response.
     *
     * @return A string message indicating successful access.
     */
    @GetMapping("/secure")
    public String secure() {
        return "This is a secure endpoint accessible with a valid JWT!";
    }

    /**
     * An endpoint to inspect the details (claims) of the authenticated JWT.
     * This also requires a valid JWT access token.
     *
     * @param jwt The authenticated Jwt object, automatically injected by Spring Security
     * if a valid JWT is present and successfully validated.
     * @return A string representation of the JWT's claims.
     */
    @GetMapping("/jwt-details")
    public String jwtDetails(@AuthenticationPrincipal Jwt jwt) {
        // The @AuthenticationPrincipal annotation allows you to directly access the
        // authenticated principal. In the case of a JWT Resource Server, this will be a Jwt object.
        // You can retrieve various claims (e.g., subject, issuer, audience, custom claims) from it.
        return "JWT details: " + jwt.getClaims();
    }
}
