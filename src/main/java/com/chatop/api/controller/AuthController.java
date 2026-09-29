package com.chatop.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chatop.api.dto.AuthResponse;
import com.chatop.api.dto.RegisterRequest;
import com.chatop.api.service.AuthService;

import jakarta.validation.Valid;

/**
 * Authentication endpoints of the API.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Creates a user account and logs the new user in.
     *
     * @param request the name, email address and password of the new user
     * @return the token of the new user
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

}
