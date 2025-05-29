# Backend Development Instructions

This directory (`demo-ticket-service`) is the project root for the Event Ticket Management backend application. When working with backend code, consider this directory as the independent project root for all operations.

## Key Information

- **Project Root**: `.` (current directory)
- **Technology Stack**: Spring Boot 3.4.5, Java 21, JPA, Spring Security
- **Build Tool**: Maven
- **Database**: H2 (in-memory for demo)

## Development Commands

When running backend-specific commands, always execute them from this directory:

```powershell
# Run the Spring Boot application
.\mvnw spring-boot:run

# Build the project
.\mvnw clean package

# Run tests
.\mvnw test
```

## Project Structure

The backend follows a clean architecture pattern with these key components:

- `api`: REST controllers and DTOs
- `core`: Domain entities, use cases, and business logic
- `infrastructure`: Implementation classes, configurations, and external integrations
- `model`: Data models and repositories
- `service`: Service implementations

## Configuration

Application configuration is managed through `src/main/resources/application.properties`.
