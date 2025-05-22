package com.ticketmanagement.demo.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * User domain entity representing system users
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends BaseEntity {
    private String username;
    private String password;
    private String roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}