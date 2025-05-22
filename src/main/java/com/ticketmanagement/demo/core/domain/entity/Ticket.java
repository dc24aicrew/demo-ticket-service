package com.ticketmanagement.demo.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Ticket domain entity representing event tickets with status
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Ticket extends BaseEntity {
    private String code;
    private UUID eventId;
    private String attendeeName;
    private TicketStatus status;
    private LocalDateTime purchaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}