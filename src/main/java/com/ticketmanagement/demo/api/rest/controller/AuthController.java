package com.ticketmanagement.demo.api.rest.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Date;

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
import com.ticketmanagement.demo.api.rest.dto.TokenVerificationResponse;
import com.ticketmanagement.demo.infrastructure.persistence.entity.UserJpaEntity;
import com.ticketmanagement.demo.infrastructure.persistence.repository.UserJpaRepository;
import com.ticketmanagement.demo.infrastructure.security.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
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
            
            if (userOpt.isEmpty()) {
                throw new BadCredentialsException("Invalid username or password");
            }
            
            String storedPassword = userOpt.get().getPassword();
            
            // Enhanced debug logging for password verification
            boolean matches = passwordEncoder.matches(authRequest.getPassword(), storedPassword);
            System.out.println("Stored password in DB: " + storedPassword);
            System.out.println("Input password: " + authRequest.getPassword());
            System.out.println("Password matches: " + matches);
            
            if (!passwordEncoder.matches(authRequest.getPassword(), storedPassword)) {
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
    
    /**
     * Verify JWT token endpoint
     * Validates a JWT token from the Authorization header and returns its claims if valid
     *
     * @param request the HTTP request containing the Authorization header
     * @return token claims if valid, or 401 Unauthorized if invalid
     */
    @PostMapping("/verify")
    public ResponseEntity<?> verifyToken(HttpServletRequest request) {
        // Extract token from Authorization header
        String header = request.getHeader(jwtTokenProvider.getAuthHeader());
        String token = jwtTokenProvider.resolveToken(header);
        
        // Check if token exists
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Missing or invalid token"));
        }
        
        // Validate token
        try {
            if (!jwtTokenProvider.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid token"));
            }
            
            // Get token claims using authentication object
            Claims claims = getClaimsFromToken(token);
            
            // Create response with token claims
            TokenVerificationResponse response = new TokenVerificationResponse(
                    claims.getSubject(),
                    claims.get("auth", String.class),
                    claims.getIssuedAt(),
                    claims.getExpiration()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Token validation failed"));
        }
    }
    
    /**
     * Helper method to get claims from a JWT token
     * This method uses the Jwts parser builder to parse the token and extract claims.
     *
     * @param token the JWT token
     * @return the token claims
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtTokenProvider.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}