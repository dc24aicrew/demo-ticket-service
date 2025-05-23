package com.ticketmanagement.demo.api.rest.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for ticket status update requests
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketStatusUpdateDto {
    @NotBlank(message = "Status is required")
    private String status;
}