# Event Ticket Management System

## Overview

The Event Ticket Management System is a web-based application designed to demonstrate efficient ticket management capabilities for event administrators. This demo provides a simple, intuitive interface for authorized personnel to search for tickets by their unique identifiers and update ticket statuses as needed.

This application addresses the critical need for real-time ticket management at events, replacing manual processes with a streamlined digital solution. The demo version showcases the core functionality that delivers immediate value.

## Key Features

- **Authentication System**: JWT-based authentication with login/logout functionality
- **Demo User Accounts**: Pre-configured user accounts for testing
- **Ticket Search Functionality**: Quick search for tickets using unique ticket codes
- **Ticket Status Management**: Update ticket status (Active/Used) with visual confirmation

## Technical Architecture

### Backend (Spring Boot 3.4.5, Java 21)

The backend is built using a clean architecture approach with the following layers:

- **Core Domain Layer**: Contains domain entities, business logic, and interfaces defining the boundaries between layers
  - `com.ticketmanagement.demo.core.domain.entity`: Domain entities
  - `com.ticketmanagement.demo.core.port.api`: Service interfaces (API ports)
  - `com.ticketmanagement.demo.core.port.spi`: Repository interfaces (SPI ports)
  - `com.ticketmanagement.demo.core.usecase`: Business logic implementations
  
- **Infrastructure Layer**: Implements technical capabilities like database access, security, and configuration
  - `com.ticketmanagement.demo.infrastructure.persistence.adapter`: Repository implementations
  - `com.ticketmanagement.demo.infrastructure.persistence.entity`: JPA entities
  - `com.ticketmanagement.demo.infrastructure.persistence.repository`: Spring Data repositories
  - `com.ticketmanagement.demo.infrastructure.security`: Security configurations
  - `com.ticketmanagement.demo.infrastructure.config`: Application configurations
  
- **API Layer**: Exposes REST endpoints for frontend communication
  - `com.ticketmanagement.demo.api.rest.controller`: REST controllers
  - `com.ticketmanagement.demo.api.rest.dto`: Data Transfer Objects
  - `com.ticketmanagement.demo.api.rest.mapper`: Entity to DTO mappers

### Data Models

- **User**: User authentication and authorization data (admin-only for the demo)
- **Ticket**: Core ticket information including unique codes and status
- **Event**: Event details that tickets are associated with

### API Overview

The system provides REST APIs for authentication, ticket management, and event management. All endpoints except login require JWT authentication.

**Key endpoints:**
- Authentication: `/api/auth/login`, `/api/auth/logout`
- Tickets: `/api/tickets/search`, `/api/tickets/{id}`, `/api/tickets/{id}/status`
- Events: `/api/events`, `/api/events/{id}`, `/api/events/{id}/tickets`

For detailed API documentation including request/response schemas, examples, and error codes, see the [API Reference](wiki/API-Reference.md).

## Demo Credentials

For testing purposes, the following demo credentials are provided:

- **Admin User**: 
  - Username: `admin`
  - Password: `admin123`
  - Roles: `ADMIN`

- **Regular User**:
  - Username: `user`
  - Password: `user123`
  - Roles: `USER`

Please note that these are for demonstration purposes only and should be changed in a production environment.

## Development with GitHub Copilot

This project is optimized for development with GitHub Copilot. Several resources are provided to help Copilot generate accurate, architecture-compliant code:

- **CONTRIBUTING.md**: Detailed contribution guidelines including clean architecture principles
- **.github/copilot/CONTEXT.md**: Technical context with code patterns for Copilot
- **.vscode/settings.json**: VS Code settings with hints for Copilot

When using Copilot to generate code for this project, ensure the suggestions adhere to:
1. Proper package placement according to clean architecture
2. Dependency direction (dependencies always point inward)
3. Established naming conventions and patterns
4. Base class extension where appropriate

## Development Setup

### Prerequisites

- JDK 21+
- Maven 3.8+
- Git

### Building and Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/dc24aicrew/demo-ticket-service.git
   cd demo-ticket-service
   ```

2. Build the application:
   ```bash
   ./mvnw clean package
   ```

3. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```
   
   Alternatively, run the JAR file:
   ```bash
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

4. Access the application:
   - API Base URL: [http://localhost:8080/api](http://localhost:8080/api)
   - H2 Console: [http://localhost:8080/api/h2-console](http://localhost:8080/api/h2-console)
     - JDBC URL: `jdbc:h2:mem:demodb`
     - Username: `sa`
     - Password: `password`

### Docker Deployment

#### Prerequisites
- Docker
- Docker Compose

#### Running with Docker Compose

1. Build and start the containers:
   ```bash
   docker-compose up -d
   ```

2. Access the applications:
   - Frontend: [http://localhost:3000](http://localhost:3000)
   - Backend API: [http://localhost:8080/api](http://localhost:8080/api)
   - H2 Console: [http://localhost:8080/api/h2-console](http://localhost:8080/api/h2-console)
     - JDBC URL: `jdbc:h2:mem:demodb`
     - Username: `sa`
     - Password: `password`

3. Stop the containers:
   ```bash
   docker-compose down
   ```

4. Rebuild the containers (after making changes):
   ```bash
   docker-compose up -d --build
   ```

### Demo Data

The application automatically loads sample data on startup:

- **Users**: 
  - admin/admin123 (ADMIN role)
  - user/user123 (USER role)

- **Events**:
  - Summer Music Festival (2 tickets)
  - Tech Conference 2023 (2 tickets)
  - Comedy Night (1 ticket)

- **Tickets**:
  - SMF-001, SMF-002 (for Summer Music Festival)
  - TC-001, TC-002 (for Tech Conference)
  - CN-001 (for Comedy Night)

## Project Structure

The project follows a clean architecture approach with the following structure:

```
demo-ticket-service/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/ticketmanagement/demo/
│   │   │       ├── api/                # API Layer
│   │   │       │   └── rest/
│   │   │       │       ├── controller/ # REST controllers
│   │   │       │       ├── dto/        # Data Transfer Objects
│   │   │       │       └── mapper/     # Entity-DTO mappers
│   │   │       ├── core/               # Core Domain Layer
│   │   │       │   ├── domain/
│   │   │       │   │   ├── entity/     # Domain entities
│   │   │       │   │   └── exception/  # Domain-specific exceptions
│   │   │       │   ├── port/
│   │   │       │   │   ├── api/        # Service interfaces (inbound)
│   │   │       │   │   └── spi/        # Repository interfaces (outbound)
│   │   │       │   └── usecase/        # Business logic implementations
│   │   │       └── infrastructure/     # Infrastructure Layer
│   │   │           ├── config/         # Application configuration
│   │   │           ├── persistence/    # Database implementation
│   │   │           │   ├── adapter/    # Repository implementations
│   │   │           │   ├── entity/     # JPA entities
│   │   │           │   └── repository/ # Spring Data repositories
│   │   │           └── security/       # Security configuration
│   │   └── resources/
│   │       └── application.properties  # Application configuration
│   └── test/                           # Test classes
└── pom.xml                             # Maven configuration
```

## Technologies Used

- **Spring Boot 3.2.0**: Core framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Database access
- **H2 Database**: In-memory database for demo purposes
- **JWT**: Token-based authentication
- **Lombok**: Boilerplate code reduction
- **Maven**: Build and dependency management
- **Docker**: Containerization
- **Docker Compose**: Container orchestration

## Frontend

The frontend is developed using:
- React 18+ with TypeScript
- Tailwind CSS for responsive UI
- Axios for API communication
- React Context for state management
- Nginx for serving the application

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

Demo Ticket Service Team - demo-ticket-service@example.com

GitHub: [https://github.com/dc24aicrew/demo-ticket-service](https://github.com/dc24aicrew/demo-ticket-service)
