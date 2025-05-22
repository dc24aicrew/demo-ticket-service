package com.ticketmanagement.demo.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * JPA entity representing tickets for events
 */
@Entity
@Table(name = "tickets")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TicketJpaEntity extends BaseJpaEntity {
    
    @Column(unique = true, nullable = false)
    private String code;
    
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventJpaEntity event;
    
    @Column(name = "attendee_name", nullable = false)
    private String attendeeName;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatusJpa status;
    
    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}