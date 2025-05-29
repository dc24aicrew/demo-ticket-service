# Ticket Management System - Technical Context for Copilot

This document provides technical context about the Ticket Management System project to guide Copilot when generating code suggestions.

## Project Overview

This is a Spring Boot application that implements a Ticket Management System using Clean Architecture principles. The system allows users to create, manage, and track support tickets.

## Technical Stack

- Java 21
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Security
- H2 Database (for development)
- JWT for authentication
- Lombok for reducing boilerplate code

## Clean Architecture Implementation

This project strictly adheres to clean architecture with the following structure:

### 1. Core Layer (Inner Layer)

- Domain entities are in `com.ticketmanagement.demo.core.domain.entity`
- Business rules are contained within domain entities
- Business interfaces (ports) are in `com.ticketmanagement.demo.core.port`:
  - API ports (`api` package): Used by controllers to access business logic
  - SPI ports (`spi` package): Used by business logic to access external resources
- Use cases (service implementations) are in `com.ticketmanagement.demo.core.usecase`

### 2. Infrastructure Layer (Outer Layer)

- Adapters implementing SPI ports are in `com.ticketmanagement.demo.infrastructure.persistence.adapter`
- JPA entities are in `com.ticketmanagement.demo.infrastructure.persistence.entity`
- Spring Data repositories are in `com.ticketmanagement.demo.infrastructure.persistence.repository`
- Security configurations are in `com.ticketmanagement.demo.infrastructure.security`
- Other configurations are in `com.ticketmanagement.demo.infrastructure.config`

### 3. API Layer (Outer Layer)

- REST controllers are in `com.ticketmanagement.demo.api.rest.controller`
- DTOs are in `com.ticketmanagement.demo.api.rest.dto`
- Mappers (between entities and DTOs) are in `com.ticketmanagement.demo.api.rest.mapper`

## Code Generation Guidelines for Copilot

When generating code for this project, please follow these guidelines:

1. **Dependency Direction**: Dependencies should always point inward (from outer to inner layers)
   - Core layer should not depend on Infrastructure or API layers
   - Use dependency inversion (interfaces in core, implementations in infrastructure)

2. **Package Placement**:
   - All domain entities must be in `core.domain.entity` package
   - All business interfaces must be in `core.port.api` or `core.port.spi` packages
   - All service implementations must be in `core.usecase` package
   - All controllers must be in `api.rest.controller` package
   - All persistence-related code must be in `infrastructure.persistence` subpackages

3. **Class Structure**:
   - Domain entities should extend `BaseEntity`
   - DTOs should extend `BaseDto`
   - Services should implement appropriate port interfaces
   - Controllers should use service ports, not direct implementations
   - Repository adapters should implement repository ports

4. **Coding Style**:
   - Prefer constructor injection (with Lombok `@RequiredArgsConstructor`)
   - Use Lombok annotations to reduce boilerplate
   - Write meaningful Javadoc comments
   - Include validation annotations on DTOs

5. **Base Classes**:
   - Extend appropriate base classes when available
   - Follow the patterns established in base classes

## Example Code Patterns

### Domain Entity Pattern
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Ticket extends BaseEntity {
    private String title;
    private String description;
    private TicketStatus status;
    private TicketPriority priority;
    private UUID assignedTo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

### Repository Port Pattern
```java
public interface TicketRepositoryPort extends BaseRepositoryPort<Ticket> {
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByAssignedTo(UUID userId);
}
```

### Service Port Pattern
```java
public interface TicketServicePort extends BaseServicePort<Ticket> {
    List<Ticket> findByStatus(TicketStatus status);
    List<Ticket> findByAssignedUser(UUID userId);
    Ticket assignTicket(UUID ticketId, UUID userId);
    Ticket updateStatus(UUID ticketId, TicketStatus status);
}
```

### Service Implementation Pattern
```java
@Service
@RequiredArgsConstructor
public class TicketService extends BaseService<Ticket> implements TicketServicePort {
    private final TicketRepositoryPort ticketRepository;
    
    @Override
    public List<Ticket> findByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }
    
    // Other method implementations
}
```

### Controller Pattern
```java
@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController extends BaseController<Ticket, TicketDto> {
    private final TicketServicePort ticketService;
    private final TicketMapper ticketMapper;
    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketDto>> findByStatus(@PathVariable TicketStatus status) {
        List<Ticket> tickets = ticketService.findByStatus(status);
        return ResponseEntity.ok(ticketMapper.toDtoList(tickets));
    }
    
    // Other endpoint implementations
}
```

Remember to always maintain the separation of concerns and ensure that dependencies flow inward according to clean architecture principles.
