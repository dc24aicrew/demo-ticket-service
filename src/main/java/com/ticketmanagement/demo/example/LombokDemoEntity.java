package com.ticketmanagement.demo.example;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This is a demo class showing Lombok features.
 * 
 * @Data - Generates getters, setters, equals, hashCode, and toString methods
 * @NoArgsConstructor - Generates a constructor with no parameters
 * @AllArgsConstructor - Generates a constructor with all parameters
 * @Builder - Implements the builder pattern
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = {"password"})
public class LombokDemoEntity {
    
    @NotNull
    private Long id;
    
    @NotBlank
    private String username;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String password; // Excluded from toString for security
    
    private boolean active;
    
    // You can still add custom methods as needed
    public boolean isValidEmail() {
        return email != null && email.contains("@");
    }
}
