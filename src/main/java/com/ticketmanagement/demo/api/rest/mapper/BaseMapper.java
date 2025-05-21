package com.ticketmanagement.demo.api.rest.mapper;

import com.ticketmanagement.demo.api.rest.dto.BaseDto;
import com.ticketmanagement.demo.core.domain.entity.BaseEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Base mapper interface for converting between domain entities and DTOs
 */
public interface BaseMapper<D extends BaseDto, E extends BaseEntity> {
    
    D toDto(E entity);
    
    E toEntity(D dto);
    
    default List<D> toDtoList(List<E> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    default List<E> toEntityList(List<D> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
