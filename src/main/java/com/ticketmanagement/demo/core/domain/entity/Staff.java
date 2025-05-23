package com.ticketmanagement.demo.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Staff domain entity representing an employee or staff member
 * in the ticket management system
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Staff extends BaseEntity {
    private String name;
    private String role;
    private String email;
    private String phone;
    private String department;
    private boolean active;
}