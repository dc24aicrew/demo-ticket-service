# Module Interactions

This document outlines the key interactions between modules in the Event Ticket Management System.

## Core Architecture Interactions

The system follows a clean architecture pattern with the following interaction flow:

```
[Web/API Layer] → [Use Case Layer] → [Domain Layer] → [Infrastructure Layer]
```

Dependencies point inward, with the domain layer being the most independent.

## Component Interactions Diagram

![Component Interactions](https://raw.githubusercontent.com/wiki/dc24aicrew/demo-ticket-service/images/component_interactions.svg)

The diagram above shows the clean architecture flow between components:

1. REST Controller (API Layer) calls Service Port (Domain Layer)
2. Service Port is implemented by Service Impl (Use Case Layer)
3. Service Impl calls Repository Port (Domain Layer)
4. Repository Port is implemented by Repository Adapter (Infrastructure Layer)
5. Repository Adapter uses JPA Repository (Infrastructure Layer)

## Key Interactions

### 1. API Request Handling

When a REST endpoint is called:

1. **Controller** receives the HTTP request
2. **Controller** converts request parameters/body to domain objects
3. **Controller** calls appropriate service port interface
4. **Service Implementation** processes the business logic
5. **Service Implementation** returns domain entities
6. **Controller** converts domain entities to DTOs
7. **Controller** returns HTTP response

Example:

```java
@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    private final TicketServicePort ticketService;
    private final TicketMapper ticketMapper;
    
    // Constructor injection
    
    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getTicketById(@PathVariable UUID id) {
        return ticketService.findById(id)
            .map(ticket -> ResponseEntity.ok(ticketMapper.toDto(ticket)))
            .orElse(ResponseEntity.notFound().build());
    }
}
```

### 2. Service Layer Interactions

The service layer implements business logic:

1. **Service Implementation** receives request from controller
2. **Service Implementation** validates input and applies business rules
3. **Service Implementation** calls repository ports for data access
4. **Service Implementation** processes data and returns results

Example:

```java
@Service
public class TicketServiceImpl implements TicketServicePort {
    private final TicketRepositoryPort repository;
    private final EventRepositoryPort eventRepository;
    
    // Constructor injection
    
    @Override
    public Optional<Ticket> findById(UUID id) {
        return repository.findById(id);
    }
    
    @Override
    public Ticket updateStatus(UUID id, TicketStatus status) {
        Ticket ticket = repository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Ticket not found"));
            
        // Business validation
        if (ticket.getStatus() == TicketStatus.USED && status == TicketStatus.ACTIVE) {
            throw new BusinessRuleViolationException("Cannot reactivate a used ticket");
        }
        
        ticket.setStatus(status);
        return repository.save(ticket);
    }
}
```

### 3. Repository Interactions

The repository layer handles data persistence:

1. **Repository Adapter** receives call from service
2. **Repository Adapter** converts domain entities to JPA entities
3. **JPA Repository** performs database operations
4. **Repository Adapter** converts JPA entities back to domain entities
5. **Repository Adapter** returns domain entities to service

Example:

```java
@Component
public class TicketRepositoryAdapter extends BaseRepositoryAdapter<Ticket, TicketJpaEntity> 
        implements TicketRepositoryPort {
    
    private final TicketJpaRepository ticketRepository;
    
    // Constructor injection
    
    @Override
    public Optional<Ticket> findByCode(String code) {
        return ticketRepository.findByCode(code)
            .map(this::mapToEntity);
    }
    
    @Override
    protected Ticket mapToEntity(TicketJpaEntity jpaEntity) {
        // Convert JPA entity to domain entity
        Ticket ticket = new Ticket();
        ticket.setId(jpaEntity.getId());
        ticket.setCode(jpaEntity.getCode());
        ticket.setStatus(jpaEntity.getStatus());
        // ... other mappings
        return ticket;
    }
    
    @Override
    protected TicketJpaEntity mapToJpaEntity(Ticket entity) {
        // Convert domain entity to JPA entity
        TicketJpaEntity jpaEntity = new TicketJpaEntity();
        jpaEntity.setId(entity.getId());
        jpaEntity.setCode(entity.getCode());
        jpaEntity.setStatus(entity.getStatus());
        // ... other mappings
        return jpaEntity;
    }
}
```

## Cross-Cutting Concerns

### Security

Security is implemented as a cross-cutting concern:

1. **JwtAuthenticationFilter** intercepts all secured endpoints
2. **JwtAuthenticationFilter** validates the JWT token
3. **SecurityContext** is populated with the authenticated user
4. **Method Security** can use `@PreAuthorize` annotations for role-based access

### Error Handling

Global exception handling:

1. **GlobalExceptionHandler** catches exceptions thrown by any layer
2. **GlobalExceptionHandler** converts exceptions to appropriate HTTP responses
3. Common error response format is maintained across all endpoints

### Transaction Management

Transaction boundaries:

1. **Service methods** are annotated with `@Transactional`
2. Spring manages transaction lifecycle
3. Repository operations within a service method execute in the same transaction

## Dependency Injection Flow

The dependency injection follows the Dependency Inversion Principle:

1. Core domain defines interfaces (ports)
2. Outer layers implement these interfaces
3. Spring DI container wires everything together

Example configuration:

```java
@Configuration
public class BeanConfig {
    
    @Bean
    public TicketServicePort ticketService(
            TicketRepositoryPort ticketRepository,
            EventRepositoryPort eventRepository) {
        return new TicketServiceImpl(ticketRepository, eventRepository);
    }
    
    @Bean
    public TicketRepositoryPort ticketRepository(
            TicketJpaRepository jpaRepository,
            TicketMapper mapper) {
        return new TicketRepositoryAdapter(jpaRepository, mapper);
    }
}
```

This configuration ensures that the clean architecture dependencies are properly maintained.

## Request-Response Flow Example

For a ticket status update request:

1. HTTP PUT request to `/api/tickets/{id}/status` with status in the body
2. `TicketController.updateStatus()` receives the request
3. Controller calls `ticketService.updateStatus(id, status)`
4. `TicketServiceImpl` validates the request
5. `TicketServiceImpl` calls `ticketRepository.findById(id)`
6. `TicketRepositoryAdapter` calls `ticketJpaRepository.findById(id)`
7. `TicketRepositoryAdapter` maps JPA entity to domain entity
8. `TicketServiceImpl` updates ticket status
9. `TicketServiceImpl` calls `ticketRepository.save(ticket)`
10. `TicketRepositoryAdapter` maps domain entity to JPA entity
11. `TicketRepositoryAdapter` calls `ticketJpaRepository.save(jpaEntity)`
12. `TicketRepositoryAdapter` maps saved JPA entity back to domain entity
13. `TicketServiceImpl` returns updated ticket
14. `TicketController` maps domain entity to DTO
15. HTTP response with updated ticket details