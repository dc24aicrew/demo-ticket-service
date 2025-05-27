package com.ticketmanagement.demo.api.rest.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO containing JWT token claims information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenVerificationResponse {
    private String subject;
    private String authorities;
    private Date issuedAt;
    private Date expiresAt;
}