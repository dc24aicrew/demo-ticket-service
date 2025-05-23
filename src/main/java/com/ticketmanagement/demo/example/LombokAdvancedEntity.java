package com.ticketmanagement.demo.example;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * Child entity demonstrating more advanced Lombok features.
 * - Inherits builder capabilities with @SuperBuilder(toBuilder = true)
 * - @Singular provides add methods for collections
 * - Compatible with parent class through consistent builder configuration
 */
@Getter
@Setter
// Removed @RequiredArgsConstructor - it conflicts with @SuperBuilder when having final fields
@AllArgsConstructor
@SuperBuilder(toBuilder = true) // Added toBuilder = true to match base class capability
@ToString(callSuper = true) // Include parent fields in toString
@EqualsAndHashCode(callSuper = true) // Include parent fields in equals/hashCode
@Accessors(chain = true) // Changed to match parent - removed fluent=true
public class LombokAdvancedEntity extends LombokBaseEntity {
    
    @NonNull
    private String name; // Non-final field to be compatible with @SuperBuilder and @AllArgsConstructor
    
    private String description;
    
    @Singular // Generates methods like "addTag" and "clearTags" in the builder
    private List<String> tags; // No initialization when using @Singular
    
    @Singular("addAttribute") // Custom singular method name
    private List<String> attributes; // No initialization when using @Singular
    
    /**
     * Sample usage of this class:
     * 
     * LombokAdvancedEntity entity = LombokAdvancedEntity.builder()
     *     .id(1L)
     *     .name("Example")
     *     .description("Advanced Lombok features")
     *     .tag("important")  // Using @Singular for collection
     *     .tag("lombok")     // Each call adds an item
     *     .addAttribute("color:blue") // Custom naming with @Singular
     *     .build();
     * 
     * // Using toBuilder to modify an existing entity
     * LombokAdvancedEntity updated = entity.toBuilder()
     *     .description("Updated description")
     *     .tag("critical")
     *     .build();
     * 
     * // Standard getters and setters with method chaining
     * String name = entity.getName();
     * entity.setDescription("Another update")
     *      .setId(123L);
     */
}
