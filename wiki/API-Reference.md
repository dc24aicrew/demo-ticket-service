# API Reference

This document provides a detailed reference for the Event Ticket Management System API endpoints.

## Authentication API

### Login

- **Endpoint**: `POST /api/auth/login`
- **Description**: Authenticates a user and returns a JWT token
- **Request Body**: Username and password
- **Response**: JWT token and user details

### Logout

- **Endpoint**: `POST /api/auth/logout`
- **Description**: Invalidates the current token
- **Auth Required**: Yes
- **Response**: Confirmation message

## Ticket API

### Search Ticket by Code

- **Endpoint**: `GET /api/tickets/search?code={ticketCode}`
- **Description**: Finds a ticket by its unique code
- **Auth Required**: Yes
- **Parameters**: `code` - Ticket unique identifier
- **Response**: Ticket details or 404 if not found

### Get Ticket by ID

- **Endpoint**: `GET /api/tickets/{id}`
- **Description**: Retrieves a ticket by its UUID
- **Auth Required**: Yes
- **Parameters**: `id` - Ticket UUID
- **Response**: Ticket details or 404 if not found

### Update Ticket Status

- **Endpoint**: `PUT /api/tickets/{id}/status`
- **Description**: Updates the status of a ticket (e.g., from Active to Used)
- **Auth Required**: Yes
- **Parameters**: `id` - Ticket UUID
- **Request Body**: New status value
- **Response**: Updated ticket details or error message

## Event API

### Get All Events

- **Endpoint**: `GET /api/events`
- **Description**: Lists all available events
- **Auth Required**: Yes
- **Response**: Array of event objects

### Get Event Tickets

- **Endpoint**: `GET /api/events/{id}/tickets`
- **Description**: Lists all tickets for a specific event
- **Auth Required**: Yes
- **Parameters**: `id` - Event UUID
- **Response**: Array of ticket objects or 404 if event not found

## Data Models

### Ticket

```
{
  "id": "UUID",
  "code": "String",
  "status": "ACTIVE|USED",
  "event": {
    "id": "UUID",
    "name": "String",
    "date": "DateTime"
  },
  "createdAt": "DateTime",
  "updatedAt": "DateTime"
}
```

### Event

```
{
  "id": "UUID",
  "name": "String",
  "date": "DateTime",
  "venue": "String",
  "ticketCount": "Integer"
}
```

## Error Handling

All API endpoints return standard HTTP status codes:

- 200: Success
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Server Error

Error responses follow a consistent format:

```
{
  "timestamp": "DateTime",
  "status": "Integer",
  "error": "String",
  "message": "String",
  "path": "String"
}
```