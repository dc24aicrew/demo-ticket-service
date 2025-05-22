package com.ticketmanagement.demo.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * JPA entity representing user data
 */
@Entity
@Table(name = "users")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserJpaEntity extends BaseJpaEntity {
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String roles;
    
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = OffsetDateTime.now(ZoneOffset.UTC);
        updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
    }
}