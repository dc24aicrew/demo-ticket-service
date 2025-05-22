# Technical Documentation

This document provides detailed technical information about the Event Ticket Management System's key modules and workflows.

## Architecture Overview

The system implements Clean Architecture principles, dividing the application into concentric layers:

1. **Domain Layer (innermost)** - Core business entities and rules
2. **Application Layer** - Use cases and business logic
3. **Infrastructure Layer** - Framework-specific implementations and adapters
4. **Presentation Layer** - REST controllers and DTOs

## Key Modules

### Core Domain Layer

The domain layer contains:

#### Domain Entities

Located in `com.ticketmanagement.demo.core.domain.entity`:

- **BaseEntity**: Abstract base class for all domain entities with common fields
- **User**: Represents a system user with authentication information
- **Event**: Represents an event with details like name, date, venue
- **Ticket**: Represents a ticket for an event with status information

#### Domain Ports

Located in `com.ticketmanagement.demo.core.port`:

- **API Ports (`api` package)**: Inbound interfaces that allow external components to use domain functionality
  - `BaseServicePort`: Common service operations interface
  - `TicketServicePort`: Ticket-specific operations like status updates
  - `EventServicePort`: Event-specific operations

- **SPI Ports (`spi` package)**: Outbound interfaces that allow the domain to use external resources
  - `BaseRepositoryPort`: Common repository operations interface
  - `TicketRepositoryPort`: Ticket-specific persistence operations
  - `EventRepositoryPort`: Event-specific persistence operations

### Use Case Layer

Located in `com.ticketmanagement.demo.core.usecase`:

- **BaseService**: Abstract base service with common CRUD operations
- **TicketService**: Implementation of ticket-related business logic
- **EventService**: Implementation of event-related business logic

### Infrastructure Layer

#### Persistence

Located in `com.ticketmanagement.demo.infrastructure.persistence`:

- **JPA Entities (`entity` package)**: Database entity models
  - `BaseJpaEntity`: Abstract base class for JPA entities
  - `UserJpaEntity`, `EventJpaEntity`, `TicketJpaEntity`: Database models

- **JPA Repositories (`repository` package)**: Spring Data JPA repositories
  - `BaseJpaRepository`: Common JPA operations
  - `UserJpaRepository`, `EventJpaRepository`, `TicketJpaRepository`: Entity-specific repositories

- **Repository Adapters (`adapter` package)**: Connect domain repositories to JPA repositories
  - `BaseRepositoryAdapter`: Abstract adapter with mapping logic
  - `TicketRepositoryAdapter`, `EventRepositoryAdapter`: Concrete adapters

#### Security

Located in `com.ticketmanagement.demo.infrastructure.security`:

- **SecurityConfig**: Spring Security configuration
- **JwtAuthenticationFilter**: Token-based authentication filter
- **CustomUserDetailsService**: User authentication service

#### Configuration

Located in `com.ticketmanagement.demo.infrastructure.config`:

- **BeanConfig**: Core component wiring and dependency injection
- **PersistenceConfig**: Database configuration
- **WebConfig**: Web-related configuration

### API Layer

Located in `com.ticketmanagement.demo.api.rest`:

- **Controllers (`controller` package)**: REST endpoints
  - `AuthController`: Authentication endpoints
  - `TicketController`: Ticket management endpoints
  - `EventController`: Event-related endpoints

- **DTOs (`dto` package)**: Data transfer objects
  - `AuthRequest`, `AuthResponse`: Authentication DTOs
  - `TicketDTO`, `EventDTO`: Entity representations for the API

- **Mappers (`mapper` package)**: Convert between domain entities and DTOs
  - `TicketMapper`, `EventMapper`: Entity-DTO mapping implementations

## Key Workflows

### Authentication Flow

1. Client sends credentials to `/api/auth/login`
2. `AuthController` receives the request
3. `CustomUserDetailsService` validates credentials
4. JWT token is generated and returned to the client
5. Subsequent API calls include the JWT in Authorization header
6. `JwtAuthenticationFilter` validates the token for protected endpoints

### Ticket Verification Workflow

1. User searches for a ticket by code via `GET /api/tickets/search?code={code}`
2. `TicketController` receives the request
3. `TicketService` processes the search request via `TicketRepositoryPort`
4. `TicketRepositoryAdapter` retrieves the ticket from the database
5. The ticket is mapped to a DTO and returned to the client

### Ticket Status Update Workflow

1. User requests ticket status update via `PUT /api/tickets/{id}/status`
2. `TicketController` receives the request
3. `TicketService` validates and processes the update
4. `TicketRepositoryAdapter` persists the updated ticket
5. The updated ticket DTO is returned to the client

## Database Schema

### User Table
- id (UUID, PK)
- username (VARCHAR)
- password (VARCHAR, encrypted)
- roles (VARCHAR)
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)

### Event Table
- id (UUID, PK)
- name (VARCHAR)
- date (TIMESTAMP)
- venue (VARCHAR)
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)

### Ticket Table
- id (UUID, PK)
- code (VARCHAR, unique)
- status (ENUM: ACTIVE, USED)
- event_id (UUID, FK to Event)
- created_at (TIMESTAMP)
- updated_at (TIMESTAMP)

## Dependency Injection

The system uses Spring's dependency injection to wire components together following Clean Architecture principles:

1. Domain ports (interfaces) are defined in the core layer
2. Implementations of these ports are provided in the outer layers
3. `BeanConfig` creates and configures the service and repository implementations
4. Components request dependencies via constructor injection

## Testing Strategy

- **Unit Tests**: Test individual components in isolation
  - `core.usecase` tests validate business logic
  - Mock dependencies using Mockito

- **Integration Tests**: Test interaction between components
  - Test repository adapters with test database
  - Test REST controllers with MockMvc

- **End-to-End Tests**: Test complete workflows
  - Use test containers for database testing
  - Validate API behavior from client perspective