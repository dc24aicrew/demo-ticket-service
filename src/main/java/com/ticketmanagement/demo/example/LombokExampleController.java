package com.ticketmanagement.demo.example;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Example controller demonstrating how Lombok can be used with Spring DTOs
 */
@RestController
@RequestMapping("/api/example")
@Slf4j
@Validated
public class LombokExampleController {

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getUsers() {
        log.info("Fetching users");
        
        // Generate dummy user data
        List<UserResponse> users = IntStream.range(1, 5)
                .mapToObj(i -> UserResponse.builder()
                        .id((long) i)
                        .username("user" + i)
                        .email("user" + i + "@example.com")
                        .active(true)
                        .build())
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("Creating user: {}", request);
        
        // In real application, you would save this to database
        UserResponse createdUser = UserResponse.builder()
                .id(UUID.randomUUID().getMostSignificantBits())
                .username(request.getUsername())
                .email(request.getEmail())
                .active(true)
                .build();
        
        return ResponseEntity.ok(createdUser);
    }
    
    /**
     * Request DTO using Lombok
     * 
     * @Data - Generates getters, setters, equals, hashCode, and toString methods
     * @Builder - Implements the builder pattern
     * This showcases validation annotations working with Lombok
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateUserRequest {
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        private String username;
        
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        private String email;
        
        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        private String password;
    }
    
    /**
     * Response DTO using Lombok
     * 
     * Using @Data for the response DTO for simplicity
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserResponse {
        private Long id;
        private String username;
        private String email;
        private boolean active;
    }
}
