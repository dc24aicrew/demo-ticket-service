package com.ticketmanagement.demo.core.port.api;

import com.ticketmanagement.demo.core.domain.entity.BaseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Base service port interface in the hexagonal architecture
 * This is the API boundary for the core business logic
 */
public interface BaseServicePort<T extends BaseEntity> {
    
    List<T> findAll();
    
    Optional<T> findById(UUID id);
    
    T save(T entity);
    
    void deleteById(UUID id);
}
