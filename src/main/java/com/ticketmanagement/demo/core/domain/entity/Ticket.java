package com.ticketmanagement.demo.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
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
    private OffsetDateTime purchaseDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}