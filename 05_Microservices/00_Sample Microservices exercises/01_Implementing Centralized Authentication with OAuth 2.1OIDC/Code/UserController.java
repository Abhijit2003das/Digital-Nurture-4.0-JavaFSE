package com.example.oauth2centralizedauth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody; // Import for @ResponseBody
import java.security.Principal;
import java.util.Collections;
import java.util.Map;

@Controller // Marks this class as a Spring MVC controller, capable of returning view names.
public class UserController {

    /**
     * Handles requests to the root URL ("/").
     * Displays user information if authenticated, otherwise prompts for login.
     *
     * @param model The Model object to pass data to the Thymeleaf view.
     * @param oauth2User The authenticated OAuth2User principal, injected by Spring Security.
     * @return The name of the Thymeleaf template ("index").
     */
    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User != null) {
            // If the user is authenticated, add their details to the model.
            // Google typically provides 'name', 'email', and 'picture' attributes.
            model.addAttribute("userName", oauth2User.getAttribute("name"));
            model.addAttribute("userEmail", oauth2User.getAttribute("email"));
            model.addAttribute("userPicture", oauth2User.getAttribute("picture"));
            model.addAttribute("userAttributes", oauth2User.getAttributes()); // All raw attributes
        } else {
            // If not authenticated, set a default user name.
            model.addAttribute("userName", "Guest");
        }
        return "index"; // Returns the 'index.html' Thymeleaf template.
    }

    /**
     * Provides a REST endpoint to return the raw authenticated user attributes as JSON.
     *
     * @param principal The authenticated OAuth2User principal.
     * @return A Map containing the user's attributes.
     */
    @GetMapping("/user-info")
    @ResponseBody // Indicates that the return value should be bound directly to the web response body.
    public Map<String, Object> userInfo(@AuthenticationPrincipal OAuth2User principal) {
        if (principal != null) {
            return principal.getAttributes();
        }
        return Collections.emptyMap();
    }

    /**
     * Provides a REST endpoint to return the raw Principal object as JSON.
     *
     * @param principal The authenticated Principal object.
     * @return The Principal object.
     */
    @GetMapping("/principal")
    @ResponseBody // Indicates that the return value should be bound directly to the web response body.
    public Principal principal(Principal principal) {
        return principal;
    }
}
