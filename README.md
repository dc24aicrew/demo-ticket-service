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

### Backend (Spring Boot 3.2.0, Java 21)

The backend is built using a clean architecture approach with the following layers:

- **Core Domain Layer**: Contains domain entities, business logic, and interfaces defining the boundaries between layers
- **Infrastructure Layer**: Implements technical capabilities like database access, security, and configuration
- **API Layer**: Exposes REST endpoints for frontend communication

### Data Models

- **User**: User authentication and authorization data (admin-only for the demo)
- **Ticket**: Core ticket information including unique codes and status
- **Event**: Event details that tickets are associated with

### API Endpoints

#### Authentication API
- POST /api/auth/login
- POST /api/auth/logout

#### Ticket API
- GET /api/tickets/search?code={ticketCode}
- GET /api/tickets/{id}
- PUT /api/tickets/{id}/status

#### Event API
- GET /api/events - Get a list of all events (JWT authenticated)
- GET /api/events/{id} - Get a specific event by ID (JWT authenticated)
- GET /api/events/{id}/tickets - Get all tickets for a specific event (JWT authenticated)

## API Usage Guide

### Authentication

All endpoints except login require JWT authentication with a token in the Authorization header.

```bash
# Login to get a JWT token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'

# Use token in subsequent requests (replace JWT-TOKEN with actual token)
curl -X GET http://localhost:8080/api/tickets \
  -H "Authorization: JWT-TOKEN"
```

### Example API Calls

#### Working with Tickets

```bash
# Search for a ticket by code
curl -X GET "http://localhost:8080/api/tickets/search?code=SMF-001" \
  -H "Authorization: JWT-TOKEN"

# Update a ticket status
curl -X PUT "http://localhost:8080/api/tickets/8f6f5d4e-1a7b-51d5-3f8a-4d6c8e0f2a4c/status" \
  -H "Authorization: JWT-TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"status": "USED"}'
```

#### Working with Events

```bash
# Get all events
curl -X GET "http://localhost:8080/api/events" \
  -H "Authorization: JWT-TOKEN"

# Get tickets for an event
curl -X GET "http://localhost:8080/api/events/5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c/tickets" \
  -H "Authorization: JWT-TOKEN"
```

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
   ```
   ./mvnw clean package
   ```

3. Run the application:
   ```
   ./mvnw spring-boot:run
   ```
   
   Alternatively, run the JAR file:
   ```
   java -jar target/demo-0.0.1-SNAPSHOT.jar
   ```

4. Access the application:
   - API Base URL: [http://localhost:8080/api](http://localhost:8080/api)
   - H2 Console: [http://localhost:8080/api/h2-console](http://localhost:8080/api/h2-console)
     - JDBC URL: `jdbc:h2:mem:demodb`
     - Username: `sa`
     - Password: `password`

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

## Frontend (Planned)

The frontend will be developed using:
- React 18+ with TypeScript
- Tailwind CSS for responsive UI
- Axios for API communication
- React Context for state management

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contact

Demo Ticket Service Team - demo-ticket-service@example.com

GitHub: [https://github.com/dc24aicrew/demo-ticket-service](https://github.com/dc24aicrew/demo-ticket-service)
