package com.ticketmanagement.demo.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * Base Data Transfer Object class for all DTOs
 * Used in the API layer for request/response objects
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseDto {
    private UUID id;
}
