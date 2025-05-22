package com.ticketmanagement.demo.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Event domain entity representing events that have tickets
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Event extends BaseEntity {
    private String name;
    private LocalDateTime date;
    private String venue;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}