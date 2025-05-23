package com.ticketmanagement.demo.api.rest.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ticketmanagement.demo.api.rest.dto.AuthRequest;
import com.ticketmanagement.demo.api.rest.dto.AuthResponse;
import com.ticketmanagement.demo.infrastructure.persistence.entity.UserJpaEntity;
import com.ticketmanagement.demo.infrastructure.persistence.repository.UserJpaRepository;
import com.ticketmanagement.demo.infrastructure.security.JwtTokenProvider;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller for handling authentication requests
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final UserJpaRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    /**
     * Login endpoint to authenticate users and return a JWT token
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest authRequest) {
        try {
            // Check if user exists
            Optional<UserJpaEntity> userOpt = userRepository.findByUsername(authRequest.getUsername());
            
            if (userOpt.isEmpty() || !passwordEncoder.matches(authRequest.getPassword(), userOpt.get().getPassword())) {
                throw new BadCredentialsException("Invalid username or password");
            }
            
            // User authenticated, generate token
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
            final String token = jwtTokenProvider.createToken(userDetails);
            
            // Return token and user details
            return ResponseEntity.ok(new AuthResponse(
                    token,
                    userOpt.get().getUsername(),
                    userOpt.get().getRoles()
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid username or password"));
        }
    }
    
    /**
     * Logout endpoint to invalidate JWT token
     * Note: Since JWT is stateless, actual invalidation would require a token blacklist
     * This is a simplified version for the demo
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return ResponseEntity.ok(response);
    }
}