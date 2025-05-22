# Frequently Asked Questions

## General Questions

### What is the Event Ticket Management System?

The Event Ticket Management System is a web-based application designed to demonstrate efficient ticket management capabilities for event administrators. It provides a simple interface for authorized personnel to search for tickets by their unique identifiers and update ticket statuses.

### What technologies are used in this project?

The project uses Spring Boot 3.2.0, Java 21, Spring Security, Spring Data JPA, H2 database, and Maven. The frontend (planned) will use React with TypeScript and Tailwind CSS.

### Is this a production-ready application?

This is currently a demonstration version focusing on core functionality. While it follows best practices and is built with a clean architecture, additional security hardening and scalability features would be needed for production use.

## Development Questions

### How do I set up the development environment?

Please refer to the [Getting Started](Getting-Started) guide for detailed setup instructions.

### What architecture does the project follow?

The project follows Clean Architecture (also known as Hexagonal Architecture or Ports and Adapters). Read more in the [Architecture](Architecture) documentation.

### How do I contribute to this project?

Check the [CONTRIBUTING.md](https://github.com/dc24aicrew/demo-ticket-service/blob/main/CONTRIBUTING.md) file for contribution guidelines.

### Where can I find API documentation?

The [API Reference](API-Reference) page contains detailed information about all available endpoints.

## Usage Questions

### How do I search for a ticket?

You can search for tickets using their unique code through the main search box or the advanced search page. See the [Usage Guide](Usage) for details.

### What ticket statuses are available?

Currently, the system supports two statuses: "Active" and "Used". More statuses may be added in future versions.

### How do I change a ticket's status?

Navigate to the ticket details page and use the "Change Status" button. Select the new status from the dropdown and confirm.

### Can I create new tickets in the system?

In the current demo version, ticket creation functionality is not exposed through the user interface. Tickets are pre-loaded in the database.

## Troubleshooting

### The application isn't starting

Make sure you have Java 21+ installed and that port 8080 is not in use. Check the console for error messages.

### I can't log in

Ensure you're using the correct username and password. Default credentials can be found in the [Getting Started](Getting-Started) guide.

### The database is empty after restarting

This is expected behavior. The demo uses an in-memory H2 database that resets when the application restarts. You can modify this behavior in the `application.properties` file.

### How do I report bugs or request features?

Please create an issue on the [GitHub repository](https://github.com/dc24aicrew/demo-ticket-service/issues) with a detailed description of the bug or feature request.