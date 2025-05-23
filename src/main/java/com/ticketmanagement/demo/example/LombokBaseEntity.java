package com.ticketmanagement.demo.example;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Base entity class demonstrating more advanced Lombok features.
 * - @SuperBuilder enables builder inheritance
 * - @Accessors(chain = true) enables method chaining for setters
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true) // Added toBuilder = true to be consistent with child class
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public abstract class LombokBaseEntity {
    
    // Removed @NonNull to allow null values for newly created entities
    protected Long id;
    
    @Getter(AccessLevel.PROTECTED)
    protected LocalDateTime createdAt;
    
    @Getter(AccessLevel.PROTECTED)
    protected LocalDateTime updatedAt;
    
    @Getter(AccessLevel.NONE) // No getter will be generated
    @Setter(AccessLevel.NONE) // No setter will be generated
    protected String internalAuditInfo;
    
    /**
     * Demonstrates custom methods can still be added
     */
    public boolean isNew() {
        return id == null;
    }
    
    /**
     * Pre-update hook
     */
    public void prepareUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
