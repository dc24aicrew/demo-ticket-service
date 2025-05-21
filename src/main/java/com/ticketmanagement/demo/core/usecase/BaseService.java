package com.ticketmanagement.demo.core.usecase;

import com.ticketmanagement.demo.core.domain.entity.BaseEntity;
import com.ticketmanagement.demo.core.port.api.BaseServicePort;
import com.ticketmanagement.demo.core.port.spi.BaseRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Abstract base service implementation in clean architecture's use case layer
 * This provides default implementations of common service methods
 */
@RequiredArgsConstructor
public abstract class BaseService<T extends BaseEntity> implements BaseServicePort<T> {

    protected final BaseRepositoryPort<T> repository;

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(UUID id) {
        return repository.findById(id);
    }

    @Override
    public T save(T entity) {
        return repository.save(entity);
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
