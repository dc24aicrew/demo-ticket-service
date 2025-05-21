package com.ticketmanagement.demo.core.port.spi;

import com.ticketmanagement.demo.core.domain.entity.BaseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Base repository port interface in the hexagonal architecture
 * This is implemented by adapters in the infrastructure layer
 */
public interface BaseRepositoryPort<T extends BaseEntity> {
    
    List<T> findAll();
    
    Optional<T> findById(UUID id);
    
    T save(T entity);
    
    void deleteById(UUID id);
}
