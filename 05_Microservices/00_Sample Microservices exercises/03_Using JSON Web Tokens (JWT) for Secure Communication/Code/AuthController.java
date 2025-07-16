package com.example.jwtauthdemo.controller;

import com.example.jwtauthdemo.payload.LoginRequest;
import com.example.jwtauthdemo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController // Marks this class as a REST controller, meaning methods return data directly.
@RequestMapping("/auth") // Base path for authentication-related endpoints.
public class AuthController {

    @Autowired // Injects the AuthenticationManager for user authentication.
    private AuthenticationManager authenticationManager;

    @Autowired // Injects the JwtTokenProvider for JWT creation.
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Handles user login requests.
     * Authenticates the user with provided credentials and, if successful, issues a JWT.
     *
     * @param loginRequest The request body containing username and password.
     * @return ResponseEntity with the JWT token or an error message if authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user using Spring Security's AuthenticationManager.
            // This will delegate to the UserDetailsService and PasswordEncoder configured in SecurityConfig.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            // Set the authenticated user in the SecurityContextHolder for the current request.
            // This is useful for subsequent filters or controllers in the same request chain.
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Get user details from the authenticated object to extract username and roles.
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Create a JWT token using the JwtTokenProvider.
            String jwt = jwtTokenProvider.createToken(userDetails.getUsername(), userDetails.getAuthorities());

            // Return the JWT token in the response.
            Map<String, String> response = new HashMap<>();
            response.put("token", jwt);
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            // If authentication fails (e.g., bad credentials), catch the exception
            // and return an unauthorized (401) response with an error message.
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid username or password");
            return ResponseEntity.status(401).body(errorResponse);
        }
    }
}
