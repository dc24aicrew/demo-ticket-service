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

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
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
    private OffsetDateTime date;
    
    @Column(nullable = false)
    private String venue;
    
    @Column(name = "created_at")
    private OffsetDateTime createdAt;
    
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;
    
    @OneToMany(mappedBy = "event")
    private final Set<TicketJpaEntity> tickets = new HashSet<>();
    
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