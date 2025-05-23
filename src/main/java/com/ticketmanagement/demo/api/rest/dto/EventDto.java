package com.ticketmanagement.demo.api.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

/**
 * Data Transfer Object for Event entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EventDto extends BaseDto {
    private String name;
    private OffsetDateTime date;
    private String venue;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}