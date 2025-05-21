package com.ticketmanagement.demo.infrastructure.persistence.adapter;

import com.ticketmanagement.demo.core.domain.entity.BaseEntity;
import com.ticketmanagement.demo.core.port.spi.BaseRepositoryPort;
import com.ticketmanagement.demo.infrastructure.persistence.entity.BaseJpaEntity;
import com.ticketmanagement.demo.infrastructure.persistence.repository.BaseJpaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Base repository adapter implementing the core port interface 
 * Connects the domain layer to the infrastructure persistence
 */
@RequiredArgsConstructor
public abstract class BaseRepositoryAdapter<T extends BaseEntity, E extends BaseJpaEntity> implements BaseRepositoryPort<T> {
    
    protected final BaseJpaRepository<E> jpaRepository;
    
    protected abstract T mapToEntity(E jpaEntity);
    
    protected abstract E mapToJpaEntity(T entity);
    
    @Override
    public List<T> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<T> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(this::mapToEntity);
    }
    
    @Override
    public T save(T entity) {
        E jpaEntity = mapToJpaEntity(entity);
        E savedEntity = jpaRepository.save(jpaEntity);
        return mapToEntity(savedEntity);
    }
    
    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
