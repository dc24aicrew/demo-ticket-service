package com.ticketmanagement.demo.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * JPA entity representing events for which tickets are issued
 */
@Entity
@Table(name = "events")
@Data
@EqualsAndHashCode(callSuper = true, exclude = "tickets")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EventJpaEntity extends BaseJpaEntity {
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private LocalDateTime date;
    
    @Column(nullable = false)
    private String venue;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "event")
    private final Set<TicketJpaEntity> tickets = new HashSet<>();
    
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