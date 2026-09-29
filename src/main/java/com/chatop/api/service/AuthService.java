package com.chatop.api.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.chatop.api.dto.AuthResponse;
import com.chatop.api.dto.RegisterRequest;
import com.chatop.api.entity.User;
import com.chatop.api.exception.ConflictException;
import com.chatop.api.repository.UserRepository;

/**
 * Handles user registration and authentication.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Creates a user account and returns a token, so that the new user is logged in right away.
     *
     * @param request the name, email address and password of the new user
     * @return the token of the new user
     * @throws ConflictException if the email address is already used
     */
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already used");
        }

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        User savedUser = userRepository.save(user);

        return new AuthResponse(jwtService.generateToken(savedUser));
    }

}
