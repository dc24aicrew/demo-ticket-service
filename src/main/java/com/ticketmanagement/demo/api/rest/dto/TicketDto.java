package com.ticketmanagement.demo.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Data Transfer Object for Ticket entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TicketDto extends BaseDto {
    private String code;
    private UUID eventId;
    private String attendeeName;
    private String status;
    private OffsetDateTime purchaseDate;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}