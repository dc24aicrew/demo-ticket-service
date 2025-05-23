# API Reference

This document provides a detailed reference for the Event Ticket Management System API endpoints.

## Authentication API

### Login

- **Endpoint**: `POST /api/auth/login`
- **Description**: Authenticates a user and returns a JWT token
- **Request Body**:
  ```json
  {
    "username": "string",
    "password": "string"
  }
  ```
- **Response**:
  ```json
  {
    "token": "JWT-token-string",
    "username": "authenticated-username",
    "roles": "comma-separated-roles"
  }
  ```
- **HTTP Status Codes**:
  - 200 OK: Authentication successful
  - 401 UNAUTHORIZED: Invalid credentials
  - 500 INTERNAL SERVER ERROR: Server error

### Logout

- **Endpoint**: `POST /api/auth/logout`
- **Description**: Invalidates the current token
- **Auth Required**: Yes (JWT token in Authorization header)
- **Request Header**:
  ```
  Authorization: ******  ```
- **Response**:
  ```json
  {
    "message": "Logged out successfully"
  }
  ```
- **HTTP Status Codes**:
  - 200 OK: Logout successful
  - 401 UNAUTHORIZED: No valid token provided
  - 403 FORBIDDEN: Invalid token

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

## Staff API

### Get All Staff Members

- **Endpoint**: `GET /api/staffs`
- **Description**: Lists all staff members
- **Auth Required**: Yes
- **Response**: Array of staff objects

### Get Staff Member by ID

- **Endpoint**: `GET /api/staffs/{id}`
- **Description**: Retrieves a staff member by their UUID
- **Auth Required**: Yes
- **Parameters**: `id` - Staff UUID
- **Response**: Staff details or 404 if not found

### Create Staff Member

- **Endpoint**: `POST /api/staffs`
- **Description**: Creates a new staff member
- **Auth Required**: Yes
- **Request Body**: Staff details (name, role, email, etc.)
- **Response**: Created staff object with 201 status code

### Update Staff Member

- **Endpoint**: `PUT /api/staffs/{id}`
- **Description**: Updates an existing staff member
- **Auth Required**: Yes
- **Parameters**: `id` - Staff UUID
- **Request Body**: Updated staff details
- **Response**: Updated staff object or 404 if not found

### Delete Staff Member

- **Endpoint**: `DELETE /api/staffs/{id}`
- **Description**: Deletes a staff member
- **Auth Required**: Yes
- **Parameters**: `id` - Staff UUID
- **Response**: 204 No Content or 404 if not found

### Get Staff by Department

- **Endpoint**: `GET /api/staffs/department/{department}`
- **Description**: Lists staff members in a specific department
- **Auth Required**: Yes
- **Parameters**: `department` - Department name
- **Response**: Array of staff objects

### Get Active Staff

- **Endpoint**: `GET /api/staffs/active`
- **Description**: Lists all active staff members
- **Auth Required**: Yes
- **Response**: Array of active staff objects

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

### Staff

```
{
  "id": "UUID",
  "name": "String",
  "role": "String",
  "email": "String",
  "phone": "String",
  "department": "String",
  "active": "Boolean"
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