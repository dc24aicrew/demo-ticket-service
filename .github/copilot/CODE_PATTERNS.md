# Code Patterns for GitHub Copilot

This file contains commonly used code patterns for the Ticket Management System project to help GitHub Copilot generate consistent, Clean Architecture-compliant code suggestions.

## Domain Entity Pattern

```java
package com.ticketmanagement.demo.core.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Ticket extends BaseEntity {
    private String code;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private UUID eventId;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    public enum TicketStatus {
        ACTIVE, USED, CANCELLED, EXPIRED
    }
    
    public enum TicketPriority {
        LOW, MEDIUM, HIGH, URGENT
    }
}
```

## SPI Repository Port Pattern

```java
package com.ticketmanagement.demo.core.port.spi;

import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.Ticket.TicketStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketRepositoryPort extends BaseRepositoryPort<Ticket> {
    Optional<Ticket> findByCode(String code);
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByEventId(UUID eventId);
    List<Ticket> findByUserId(UUID userId);
}
```

## API Service Port Pattern

```java
package com.ticketmanagement.demo.core.port.api;

import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.Ticket.TicketStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketServicePort extends BaseServicePort<Ticket> {
    Optional<Ticket> findByCode(String code);
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByEventId(UUID eventId);
    List<Ticket> findByUserId(UUID userId);
    Ticket updateStatus(UUID id, TicketStatus newStatus);
}
```

## Service Implementation Pattern

```java
package com.ticketmanagement.demo.core.usecase;

import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.Ticket.TicketStatus;
import com.ticketmanagement.demo.core.domain.exception.DomainExceptions.EntityNotFoundException;
import com.ticketmanagement.demo.core.port.api.TicketServicePort;
import com.ticketmanagement.demo.core.port.spi.TicketRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketService extends BaseService<Ticket> implements TicketServicePort {
    private final TicketRepositoryPort ticketRepository;

    @Override
    public Optional<Ticket> findByCode(String code) {
        return ticketRepository.findByCode(code);
    }

    @Override
    public List<Ticket> findByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }

    @Override
    public List<Ticket> findByEventId(UUID eventId) {
        return ticketRepository.findByEventId(eventId);
    }

    @Override
    public List<Ticket> findByUserId(UUID userId) {
        return ticketRepository.findByUserId(userId);
    }

    @Override
    public Ticket updateStatus(UUID id, TicketStatus newStatus) {
        Ticket ticket = findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ticket with id " + id + " not found"));
        
        ticket.setStatus(newStatus);
        ticket.setUpdatedAt(LocalDateTime.now());
        
        return repository.save(ticket);
    }
}
```

## JPA Entity Pattern

```java
package com.ticketmanagement.demo.infrastructure.persistence.entity;

import com.ticketmanagement.demo.core.domain.entity.Ticket.TicketStatus;
import com.ticketmanagement.demo.core.domain.entity.Ticket.TicketPriority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TicketJpaEntity extends BaseJpaEntity {
    
    @Column(unique = true, nullable = false)
    private String code;
    
    @Column(length = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority;
    
    @Column(name = "event_id", nullable = false)
    private UUID eventId;
    
    @Column(name = "user_id")
    private UUID userId;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
```

## Repository Adapter Pattern

```java
package com.ticketmanagement.demo.infrastructure.persistence.adapter;

import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.Ticket.TicketStatus;
import com.ticketmanagement.demo.core.port.spi.TicketRepositoryPort;
import com.ticketmanagement.demo.infrastructure.persistence.entity.TicketJpaEntity;
import com.ticketmanagement.demo.infrastructure.persistence.repository.TicketJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TicketRepositoryAdapter extends BaseRepositoryAdapter<Ticket, TicketJpaEntity> implements TicketRepositoryPort {
    
    private final TicketJpaRepository ticketJpaRepository;
    
    @Override
    public Optional<Ticket> findByCode(String code) {
        return ticketJpaRepository.findByCode(code)
                .map(this::mapToDomainEntity);
    }
    
    @Override
    public List<Ticket> findByStatus(TicketStatus status) {
        return ticketJpaRepository.findByStatus(status)
                .stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Ticket> findByEventId(UUID eventId) {
        return ticketJpaRepository.findByEventId(eventId)
                .stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Ticket> findByUserId(UUID userId) {
        return ticketJpaRepository.findByUserId(userId)
                .stream()
                .map(this::mapToDomainEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    protected Ticket mapToDomainEntity(TicketJpaEntity jpaEntity) {
        return Ticket.builder()
                .id(jpaEntity.getId())
                .code(jpaEntity.getCode())
                .description(jpaEntity.getDescription())
                .status(jpaEntity.getStatus())
                .priority(jpaEntity.getPriority())
                .eventId(jpaEntity.getEventId())
                .userId(jpaEntity.getUserId())
                .createdAt(jpaEntity.getCreatedAt())
                .updatedAt(jpaEntity.getUpdatedAt())
                .build();
    }
    
    @Override
    protected TicketJpaEntity mapToJpaEntity(Ticket domainEntity) {
        return TicketJpaEntity.builder()
                .id(domainEntity.getId())
                .code(domainEntity.getCode())
                .description(domainEntity.getDescription())
                .status(domainEntity.getStatus())
                .priority(domainEntity.getPriority())
                .eventId(domainEntity.getEventId())
                .userId(domainEntity.getUserId())
                .createdAt(domainEntity.getCreatedAt())
                .updatedAt(domainEntity.getUpdatedAt())
                .build();
    }
}
```

## JPA Repository Pattern

```java
package com.ticketmanagement.demo.infrastructure.persistence.repository;

import com.ticketmanagement.demo.core.domain.entity.Ticket.TicketStatus;
import com.ticketmanagement.demo.infrastructure.persistence.entity.TicketJpaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketJpaRepository extends BaseJpaRepository<TicketJpaEntity> {
    Optional<TicketJpaEntity> findByCode(String code);
    List<TicketJpaEntity> findByStatus(TicketStatus status);
    List<TicketJpaEntity> findByEventId(UUID eventId);
    List<TicketJpaEntity> findByUserId(UUID userId);
}
```

## DTO Pattern

```java
package com.ticketmanagement.demo.api.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ticketmanagement.demo.core.domain.entity.Ticket.TicketStatus;
import com.ticketmanagement.demo.core.domain.entity.Ticket.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TicketDto extends BaseDto {
    @NotBlank(message = "Ticket code is required")
    private String code;
    
    private String description;
    
    @NotNull(message = "Status is required")
    private TicketStatus status;
    
    @NotNull(message = "Priority is required")
    private TicketPriority priority;
    
    @NotNull(message = "Event ID is required")
    private UUID eventId;
    
    private UUID userId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
}
```

## Mapper Pattern

```java
package com.ticketmanagement.demo.api.rest.mapper;

import com.ticketmanagement.demo.api.rest.dto.TicketDto;
import com.ticketmanagement.demo.core.domain.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper implements BaseMapper<TicketDto, Ticket> {
    
    @Override
    public TicketDto toDto(Ticket entity) {
        return TicketDto.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .priority(entity.getPriority())
                .eventId(entity.getEventId())
                .userId(entity.getUserId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
    
    @Override
    public Ticket toEntity(TicketDto dto) {
        return Ticket.builder()
                .id(dto.getId())
                .code(dto.getCode())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .eventId(dto.getEventId())
                .userId(dto.getUserId())
                .createdAt(dto.getCreatedAt())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
```

## Controller Pattern

```java
package com.ticketmanagement.demo.api.rest.controller;

import com.ticketmanagement.demo.api.rest.dto.TicketDto;
import com.ticketmanagement.demo.api.rest.mapper.TicketMapper;
import com.ticketmanagement.demo.core.domain.entity.Ticket;
import com.ticketmanagement.demo.core.domain.entity.Ticket.TicketStatus;
import com.ticketmanagement.demo.core.port.api.TicketServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController extends BaseController<Ticket, TicketDto> {
    
    private final TicketServicePort ticketService;
    private final TicketMapper ticketMapper;
    
    @Override
    protected TicketMapper getMapper() {
        return ticketMapper;
    }
    
    @GetMapping("/search")
    public ResponseEntity<TicketDto> findByCode(@RequestParam String code) {
        return ticketService.findByCode(code)
                .map(ticketMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketDto>> findByStatus(@PathVariable TicketStatus status) {
        List<Ticket> tickets = ticketService.findByStatus(status);
        return ResponseEntity.ok(ticketMapper.toDtoList(tickets));
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<TicketDto> updateStatus(
            @PathVariable UUID id,
            @RequestParam TicketStatus status) {
        Ticket updatedTicket = ticketService.updateStatus(id, status);
        return ResponseEntity.ok(ticketMapper.toDto(updatedTicket));
    }
    
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<TicketDto>> findByEventId(@PathVariable UUID eventId) {
        List<Ticket> tickets = ticketService.findByEventId(eventId);
        return ResponseEntity.ok(ticketMapper.toDtoList(tickets));
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketDto>> findByUserId(@PathVariable UUID userId) {
        List<Ticket> tickets = ticketService.findByUserId(userId);
        return ResponseEntity.ok(ticketMapper.toDtoList(tickets));
    }
}
```
