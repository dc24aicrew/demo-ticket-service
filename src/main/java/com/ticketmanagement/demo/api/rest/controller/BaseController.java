package com.ticketmanagement.demo.api.rest.controller;

import com.ticketmanagement.demo.api.rest.dto.BaseDto;
import com.ticketmanagement.demo.api.rest.mapper.BaseMapper;
import com.ticketmanagement.demo.core.domain.entity.BaseEntity;
import com.ticketmanagement.demo.core.port.api.BaseServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Base controller providing standard REST endpoints
 */
@RequiredArgsConstructor
public abstract class BaseController<T extends BaseEntity, D extends BaseDto> {

    protected final BaseServicePort<T> service;
    protected final BaseMapper<D, T> mapper;

    @GetMapping
    public ResponseEntity<List<D>> findAll() {
        List<T> entities = service.findAll();
        return ResponseEntity.ok(mapper.toDtoList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> findById(@PathVariable UUID id) {
        return service.findById(id)
                .map(entity -> ResponseEntity.ok(mapper.toDto(entity)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<D> save(@RequestBody D dto) {
        T entity = mapper.toEntity(dto);
        T savedEntity = service.save(entity);
        return new ResponseEntity<>(mapper.toDto(savedEntity), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@PathVariable UUID id, @RequestBody D dto) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        T entity = mapper.toEntity(dto);
        // Ensure the ID from path is used
        // We need to use reflection here as BaseEntity is generic
        try {
            java.lang.reflect.Method setId = entity.getClass().getMethod("setId", UUID.class);
            setId.invoke(entity, id);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
        
        T savedEntity = service.save(entity);
        return ResponseEntity.ok(mapper.toDto(savedEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        if (service.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
