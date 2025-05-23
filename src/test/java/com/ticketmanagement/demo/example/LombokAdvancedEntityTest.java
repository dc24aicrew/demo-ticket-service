package com.ticketmanagement.demo.example;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

/**
 * Tests demonstrating how to work with Lombok-generated code in tests.
 */
@Slf4j
class LombokAdvancedEntityTest {

    @Test
    void testBuilderPattern() {
        // Using the builder pattern (SuperBuilder) to create our entity
        LombokAdvancedEntity entity = LombokAdvancedEntity.builder()
                .id(1L)
                .name("TestEntity")
                .description("Testing the Builder pattern")
                .tag("test")
                .tag("lombok")
                .addAttribute("version:1.0")
                .build();
        
        // Verify the builder worked correctly
        assertEquals(1L, entity.getId());
        assertEquals("TestEntity", entity.getName());
        assertEquals("Testing the Builder pattern", entity.getDescription());
        assertEquals(Arrays.asList("test", "lombok"), entity.getTags());
        assertEquals(Arrays.asList("version:1.0"), entity.getAttributes());
        
        log.info("Created entity: {}", entity);
    }
    
    @Test
    void testMethodChaining() {
        // Using method chaining (@Accessors(chain = true))
        LombokAdvancedEntity entity = LombokAdvancedEntity.builder()
            .name("Test") // Must provide a name due to @NonNull annotation
            // Not initializing id (now that @NonNull is removed)
            .build();
        
        // Method chaining with setters
        entity.setId(2L);
        
        // Test the values
        assertEquals(2L, entity.getId());
        assertEquals("Test", entity.getName());
        
        log.info("Created entity with method chaining: {}", entity);
    }
    
    @Test
    void testEqualsAndHashCode() {
        // Testing @EqualsAndHashCode
        LombokAdvancedEntity entity1 = LombokAdvancedEntity.builder()
                .id(1L)
                .name("Same")
                .build();
        
        LombokAdvancedEntity entity2 = LombokAdvancedEntity.builder()
                .id(1L)
                .name("Same")
                .build();
        
        LombokAdvancedEntity entity3 = LombokAdvancedEntity.builder()
                .id(2L)
                .name("Different")
                .build();
        
        // Test equals and hashCode
        assertEquals(entity1, entity2);
        assertEquals(entity1.hashCode(), entity2.hashCode());
        assertNotEquals(entity1, entity3);
        assertNotEquals(entity1.hashCode(), entity3.hashCode());
    }
    
    @Test
    void testInheritedMethods() {
        // Testing inherited methods from the base class
        LombokAdvancedEntity entity = LombokAdvancedEntity.builder()
            .name("TestEntity")
            // Not setting id to test isNew() method
            .build();
        
        // isNew() is inherited from LombokBaseEntity
        assertTrue(entity.isNew());
        
        entity.setId(5L);
        assertFalse(entity.isNew());
        
        // Call the prepareUpdate() method from parent
        entity.prepareUpdate();
        
        // We can't directly assert on protected fields like updatedAt,
        // but we can verify the method executed without errors
        assertNotNull(entity);
    }
}
