# Contributing to Ticket Management System

This document provides guidance for contributors to the Ticket Management System project, including architecture principles, code structure, and development guidelines.

## Clean Architecture

This project strictly follows clean architecture principles to ensure separation of concerns, testability, and maintainability.

### Architecture Layers

1. **API Layer** (`com.ticketmanagement.demo.api`)

   - Handles HTTP requests, responses, and data transformation
   - Contains controllers, DTOs (Data Transfer Objects), and mappers
   - Depends on the Core layer but not on the Infrastructure layer

2. **Core Layer** (`com.ticketmanagement.demo.core`)

   - Contains business logic and domain models
   - Subpackages:
     - `domain`: Domain entities and value objects
     - `port`: Interface definitions (API and SPI ports)
     - `usecase`: Service implementations of business logic

3. **Infrastructure Layer** (`com.ticketmanagement.demo.infrastructure`)
   - Implements external dependencies and frameworks integration
   - Subpackages:
     - `persistence`: Database access implementations
     - `security`: Authentication and authorization
     - `config`: Configuration classes

### Dependency Rule

The fundamental rule is that source code dependencies can only point inward:

- External layers can depend on inner layers
- Inner layers cannot depend on outer layers
- The Core layer has no dependencies on frameworks or external libraries

## Package Structure

```
com.ticketmanagement.demo
├── api
│   └── rest
│       ├── controller  // REST API endpoints
│       ├── dto         // Data Transfer Objects
│       └── mapper      // Conversion between DTOs and domain entities
├── core
│   ├── domain
│   │   ├── entity      // Core business entities
│   │   └── exception   // Domain-specific exceptions
│   ├── port
│   │   ├── api         // Service interfaces (API ports)
│   │   └── spi         // Repository interfaces (SPI ports)
│   └── usecase         // Service implementations
└── infrastructure
    ├── config          // Application configuration
    ├── persistence
    │   ├── adapter     // Repository implementations
    │   ├── entity      // JPA entities
    │   └── repository  // Spring Data repositories
    └── security        // Security configurations
```

## Development Guidelines

### 1. Creating a New Feature

Follow this sequence:

1. Define domain entities in `core.domain.entity`
2. Define repository interfaces (ports) in `core.port.spi`
3. Define service interfaces (ports) in `core.port.api`
4. Implement services in `core.usecase`
5. Create JPA entities in `infrastructure.persistence.entity`
6. Implement repository adapters in `infrastructure.persistence.adapter`
7. Define DTOs in `api.rest.dto`
8. Implement mappers in `api.rest.mapper`
9. Implement controllers in `api.rest.controller`

### 2. Naming Conventions

- **Entities**: Represent domain objects, named without suffixes (e.g., `Ticket`)
- **DTOs**: Use the `Dto` suffix (e.g., `TicketDto`)
- **Service Interfaces**: Use the `Port` suffix for ports (e.g., `TicketServicePort`)
- **Service Implementations**: Use the `Service` suffix (e.g., `TicketService`)
- **Repository Interfaces**: Use `Port` suffix (e.g., `TicketRepositoryPort`)
- **Repository Implementations**: Use `Adapter` suffix (e.g., `TicketRepositoryAdapter`)
- **JPA Entities**: Use `JpaEntity` suffix (e.g., `TicketJpaEntity`)

### 3. Testing Guidelines

- **Unit Tests**: Test each layer in isolation with mocks for dependencies
- **Integration Tests**: Test interactions between layers
- **End-to-End Tests**: Test complete flows through the system

### 4. Code Style

- Use meaningful variable and method names
- Write comprehensive Javadoc comments
- Follow the SOLID principles
- Keep methods short and focused on a single responsibility
- Use constructor injection for dependencies

## Example Implementation Flow

Here's an example of implementing a Ticket feature:

```java
// 1. Domain Entity
package com.ticketmanagement.demo.core.domain.entity;

public class Ticket extends BaseEntity {
    private String title;
    private String description;
    private Priority priority;
    private Status status;
    // getters, setters, constructors
}

// 2. Repository Port
package com.ticketmanagement.demo.core.port.spi;

public interface TicketRepositoryPort extends BaseRepositoryPort<Ticket> {
    List<Ticket> findByStatus(Status status);
}

// 3. Service Port
package com.ticketmanagement.demo.core.port.api;

public interface TicketServicePort extends BaseServicePort<Ticket> {
    List<Ticket> findByStatus(Status status);
    Ticket updateStatus(UUID id, Status newStatus);
}

// Continue with service implementation, JPA entities, repository adapter, DTOs, etc.
```

Remember to register any new components with Spring in appropriate configuration classes when needed.
