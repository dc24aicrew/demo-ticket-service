# Getting Started

This guide will help you set up the Event Ticket Management System for development and testing.

## Prerequisites

Before you begin, ensure you have the following installed:

- JDK 21 or later
- Maven 3.8.0 or later
- Git
- Your favorite IDE (IntelliJ IDEA, Eclipse, VS Code)

## Installation

### Clone the Repository

```bash
git clone https://github.com/dc24aicrew/demo-ticket-service.git
cd demo-ticket-service
```

### Build the Application

```bash
./mvnw clean package
```

This command compiles the code, runs tests, and packages the application into a JAR file.

### Run the Application

You can run the application directly using Maven:

```bash
./mvnw spring-boot:run
```

Alternatively, you can run the JAR file:

```bash
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

## Accessing the Application

Once the application is running, you can access:

- Main application: [http://localhost:8080](http://localhost:8080)
- H2 Console (database): [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - JDBC URL: `jdbc:h2:mem:demodb`
  - Username: `sa`
  - Password: `password`

## Default User Accounts

For testing purposes, the system comes with pre-configured accounts:

| Username | Password | Role  |
|----------|----------|-------|
| admin    | admin    | ADMIN |
| user     | password | USER  |

## Next Steps

- Explore the [Architecture](Architecture) to understand the system design
- Check out the [Usage](Usage) guide to learn how to use the application
- Review the [API Reference](API-Reference) for backend integration

## Troubleshooting

### Common Issues

#### Application Won't Start

- Ensure the required ports (8080) are not in use
- Check if you have the correct Java version installed
- Verify Maven is correctly configured

#### Database Connection Issues

- Check if H2 console configurations are correct
- Ensure the application.properties file has the correct database settings

### Getting Help

If you encounter issues not covered here, please:

- Check existing [GitHub Issues](https://github.com/dc24aicrew/demo-ticket-service/issues)
- Create a new issue with detailed information about your problem