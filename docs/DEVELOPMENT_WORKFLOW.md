# Development Workflow

This document outlines the recommended workflows for developing, testing, and contributing to the Event Ticket Management System.

## Development Environment Setup

### Prerequisites

- JDK 21+
- Maven 3.8+
- Git
- IDE (IntelliJ IDEA, Eclipse, or VS Code recommended)

### IDE Setup

#### IntelliJ IDEA

1. Enable Lombok plugin
2. Enable annotation processing
3. Import project as Maven project
4. Set JDK 21 as project SDK

#### Eclipse

1. Install Lombok plugin
2. Enable annotation processing
3. Import as Maven project

#### VS Code

1. Install Java Extension Pack
2. Install Lombok Annotations Support
3. Open folder as Maven project

## Development Workflow

### 1. Create a Feature Branch

```bash
git checkout -b feature/your-feature-name
```

### 2. Implement Changes

Follow the clean architecture principles:

1. Define domain entities and interfaces first (if needed)
2. Implement use cases (service layer)
3. Implement repositories and adapters
4. Implement controllers and DTOs

### 3. Run Tests Locally

```bash
./mvnw test
```

For specific test classes:

```bash
./mvnw test -Dtest=TestClassName
```

### 4. Build and Run Locally

```bash
./mvnw spring-boot:run
```

### 5. Submit a Pull Request

1. Push your branch to the repository
2. Create a pull request with a description of changes
3. Link any related issues

## Code Structure Guidelines

### Package Structure

Follow the established package structure:

```
com.ticketmanagement.demo/
├── api/                # API Layer
│   └── rest/
│       ├── controller/ # REST controllers
│       ├── dto/        # Data Transfer Objects
│       └── mapper/     # Entity-DTO mappers
├── core/               # Core Domain Layer
│   ├── domain/
│   │   ├── entity/     # Domain entities
│   │   └── exception/  # Domain-specific exceptions
│   ├── port/
│   │   ├── api/        # Service interfaces (inbound)
│   │   └── spi/        # Repository interfaces (outbound)
│   └── usecase/        # Business logic implementations
└── infrastructure/     # Infrastructure Layer
    ├── config/         # Application configuration
    ├── persistence/    # Database implementation
    │   ├── adapter/    # Repository implementations
    │   ├── entity/     # JPA entities
    │   └── repository/ # Spring Data repositories
    └── security/       # Security configuration
```

### Naming Conventions

- **Controllers**: Suffix with `Controller` (e.g., `TicketController`)
- **Services**: Suffix with `Service` or `ServiceImpl` (e.g., `TicketServiceImpl`)
- **Repositories**: Suffix with `Repository` (e.g., `TicketRepository`)
- **DTOs**: Suffix with `DTO` (e.g., `TicketDTO`)
- **Entities**: No suffix (e.g., `Ticket`)
- **JPA Entities**: Suffix with `JpaEntity` (e.g., `TicketJpaEntity`)
- **Interfaces/Ports**: Suffix with `Port` (e.g., `TicketRepositoryPort`)

### Code Style

- Use 4 spaces for indentation
- Follow Google Java Style Guide
- Use Lombok to reduce boilerplate
- Document public methods with Javadoc

## Testing Guidelines

### Test Types

#### Unit Tests

- Test individual components in isolation
- Mock dependencies
- Focus on business logic and edge cases

Example unit test structure:

```java
@ExtendWith(MockitoExtension.class)
class TicketServiceImplTest {
    @Mock
    private TicketRepositoryPort ticketRepository;
    
    @Mock
    private EventRepositoryPort eventRepository;
    
    @InjectMocks
    private TicketServiceImpl ticketService;
    
    @Test
    void shouldUpdateTicketStatus() {
        // Arrange
        UUID id = UUID.randomUUID();
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setStatus(TicketStatus.ACTIVE);
        
        when(ticketRepository.findById(id)).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(i -> i.getArgument(0));
        
        // Act
        Ticket result = ticketService.updateStatus(id, TicketStatus.USED);
        
        // Assert
        assertEquals(TicketStatus.USED, result.getStatus());
        verify(ticketRepository).save(ticket);
    }
}
```

#### Integration Tests

- Test interactions between components
- Use test databases
- Focus on repository adapters and web layers

Example integration test structure:

```java
@SpringBootTest
@AutoConfigureMockMvc
class TicketControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void shouldGetTicketById() throws Exception {
        // Arrange
        UUID id = UUID.randomUUID();
        
        // Act & Assert
        mockMvc.perform(get("/api/tickets/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }
}
```

### Test Coverage

- Aim for at least 80% test coverage
- Focus on testing business logic and edge cases
- Use JaCoCo for measuring test coverage

## Continuous Integration

### GitHub Actions

The project uses GitHub Actions for CI/CD:

1. **Build and Test**: Runs on every push and pull request
2. **Code Quality**: SonarQube analysis
3. **Security Scan**: Dependency and code vulnerability scanning

### Local CI Verification

Before pushing changes, verify locally:

```bash
./mvnw clean verify
```

## Documentation Guidelines

### Code Documentation

- Use Javadoc for public classes and methods
- Document complex algorithms and business rules
- Keep comments up to date with code changes

### Project Documentation

- Update README.md with significant changes
- Document API changes in the API Reference
- Update architecture documentation when structures change

## Common Development Tasks

### Adding a New Entity

1. Create domain entity in `core.domain.entity`
2. Create repository port in `core.port.spi`
3. Create service port in `core.port.api`
4. Implement service in `core.usecase`
5. Create JPA entity in `infrastructure.persistence.entity`
6. Create JPA repository in `infrastructure.persistence.repository`
7. Create repository adapter in `infrastructure.persistence.adapter`
8. Create DTO in `api.rest.dto`
9. Create mapper in `api.rest.mapper`
10. Create controller in `api.rest.controller`
11. Add tests for all components

### Adding a New Endpoint

1. Determine if new service method is needed
2. Create DTO if needed
3. Add mapper method if needed
4. Implement controller endpoint
5. Add unit and integration tests
6. Update API documentation

## Troubleshooting

### Common Issues

#### Application Won't Start

- Check port conflicts
- Verify database configuration
- Check logs for startup errors

#### Tests Failing

- Check test dependencies
- Verify mocks and stubbing
- Clean and rebuild project

#### Database Issues

- Check connection properties
- Verify schema matches entity definitions
- Check for migration issues