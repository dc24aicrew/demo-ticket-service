package com.ticketmanagement.demo.api.rest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Data Transfer Object for Staff API operations
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class StaffDTO extends BaseDto {
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Role is required")
    private String role;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;
    
    private String phone;
    
    private String department;
    
    @NotNull(message = "Active status is required")
    private Boolean active;
}