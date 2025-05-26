# API Reference

This document provides a comprehensive reference for the Event Ticket Management System API endpoints.

## Base URL

```
http://localhost:8080/api
```

## Authentication

All endpoints except `/api/auth/login` require JWT authentication. Include the JWT token in the Authorization header:

```
Authorization: Bearer YOUR_JWT_TOKEN
```

## Quick Start

### 1. Login to get a JWT token
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}'
```

### 2. Use the token in subsequent requests
```bash
curl -X GET http://localhost:8080/api/events \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

## Demo Credentials

For testing purposes, use these pre-configured accounts:

- **Admin User**: 
  - Username: `admin`
  - Password: `admin123`
  - Roles: `ADMIN`
  - Can access all endpoints

- **Regular User**:
  - Username: `user`
  - Password: `user123`
  - Roles: `USER`
  - Limited access to certain endpoints

## Demo Data

The system comes pre-loaded with sample data:

### Events
- **Summer Music Festival** (ID: `5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c`)
  - Tickets: `SMF-001`, `SMF-002`
- **Tech Conference 2024** (ID: `7a1c4e8f-2b5d-49a3-8c6e-3f7b9d1f5a9c`)
  - Tickets: `TC-001`, `TC-002`
- **Comedy Night** (ID: `3f8b1d7e-9c4a-58f2-1e5d-8a2f4c7e9b1a`)
  - Tickets: `CN-001`

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
  Authorization: Bearer YOUR_JWT_TOKEN
  ```
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
- **Parameters**: 
  - `code` (query parameter) - Ticket unique identifier (e.g., "SMF-001")
- **Example Request**:
  ```bash
  curl -X GET "http://localhost:8080/api/tickets/search?code=SMF-001" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```
- **Response**: Ticket details or 404 if not found
- **Example Response**:
  ```json
  {
    "id": "8f6f5d4e-1a7b-51d5-3f8a-4d6c8e0f2a4c",
    "code": "SMF-001",
    "status": "ACTIVE",
    "event": {
      "id": "5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c",
      "name": "Summer Music Festival",
      "date": "2024-07-15T18:00:00Z"
    },
    "createdAt": "2024-01-15T10:00:00Z",
    "updatedAt": "2024-01-15T10:00:00Z"
  }
  ```

### Get Ticket by ID

- **Endpoint**: `GET /api/tickets/{id}`
- **Description**: Retrieves a ticket by its UUID
- **Auth Required**: Yes
- **Parameters**: 
  - `id` (path parameter) - Ticket UUID
- **Example Request**:
  ```bash
  curl -X GET "http://localhost:8080/api/tickets/8f6f5d4e-1a7b-51d5-3f8a-4d6c8e0f2a4c" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```
- **Response**: Ticket details or 404 if not found

### Update Ticket Status

- **Endpoint**: `PATCH /api/tickets/{id}/status`
- **Description**: Updates the status of a ticket (e.g., from ACTIVE to USED)
- **Auth Required**: Yes
- **Parameters**: 
  - `id` (path parameter) - Ticket UUID
- **Request Body**: 
  ```json
  {
    "status": "USED"
  }
  ```
- **Example Request**:
  ```bash
  curl -X PATCH "http://localhost:8080/api/tickets/8f6f5d4e-1a7b-51d5-3f8a-4d6c8e0f2a4c/status" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN" \
    -H "Content-Type: application/json" \
    -d '{"status": "USED"}'
  ```
- **Response**: Updated ticket details or error message
- **Example Response**:
  ```json
  {
    "id": "8f6f5d4e-1a7b-51d5-3f8a-4d6c8e0f2a4c",
    "code": "SMF-001",
    "status": "USED",
    "event": {
      "id": "5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c",
      "name": "Summer Music Festival",
      "date": "2024-07-15T18:00:00Z"
    },
    "createdAt": "2024-01-15T10:00:00Z",
    "updatedAt": "2024-05-26T14:30:00Z"
  }
  ```

### Search Tickets by Multiple Criteria

- **Endpoint**: `GET /api/tickets/search`
- **Description**: Advanced search for tickets with multiple filter options
- **Auth Required**: Yes
- **Parameters** (all optional query parameters):
  - `code` - Search by ticket code (takes priority)
  - `eventId` - Filter by event UUID
  - `attendeeName` - Filter by attendee name
  - `status` - Filter by ticket status (ACTIVE, USED)
- **Example Requests**:
  ```bash
  # Search by event ID
  curl -X GET "http://localhost:8080/api/tickets/search?eventId=5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN"

  # Search by status
  curl -X GET "http://localhost:8080/api/tickets/search?status=ACTIVE" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN"

  # Search by attendee name
  curl -X GET "http://localhost:8080/api/tickets/search?attendeeName=John" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN"

  # Get all tickets (no parameters)
  curl -X GET "http://localhost:8080/api/tickets/search" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```
- **Response**: Array of matching tickets or empty array if none found

### Get All Tickets

- **Endpoint**: `GET /api/tickets`
- **Description**: Retrieves all tickets in the system
- **Auth Required**: Yes
- **Example Request**:
  ```bash
  curl -X GET "http://localhost:8080/api/tickets" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```
- **Response**: Array of all ticket objects

### Create New Ticket

- **Endpoint**: `POST /api/tickets`
- **Description**: Creates a new ticket
- **Auth Required**: Yes
- **Request Body**: 
  ```json
  {
    "code": "NEW-001",
    "eventId": "5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c",
    "attendeeName": "John Doe",
    "status": "ACTIVE"
  }
  ```
- **Example Request**:
  ```bash
  curl -X POST "http://localhost:8080/api/tickets" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN" \
    -H "Content-Type: application/json" \
    -d '{"code": "NEW-001", "eventId": "5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c", "attendeeName": "John Doe", "status": "ACTIVE"}'
  ```
- **Response**: Created ticket object with assigned UUID

### Update Ticket

- **Endpoint**: `PUT /api/tickets/{id}`
- **Description**: Updates an entire ticket object
- **Auth Required**: Yes
- **Parameters**: 
  - `id` (path parameter) - Ticket UUID
- **Request Body**: Complete ticket object
- **Example Request**:
  ```bash
  curl -X PUT "http://localhost:8080/api/tickets/8f6f5d4e-1a7b-51d5-3f8a-4d6c8e0f2a4c" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN" \
    -H "Content-Type: application/json" \
    -d '{"code": "SMF-001", "eventId": "5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c", "attendeeName": "Jane Doe", "status": "ACTIVE"}'
  ```
- **Response**: Updated ticket object

### Delete Ticket

- **Endpoint**: `DELETE /api/tickets/{id}`
- **Description**: Deletes a ticket by its UUID
- **Auth Required**: Yes
- **Parameters**: 
  - `id` (path parameter) - Ticket UUID
- **Example Request**:
  ```bash
  curl -X DELETE "http://localhost:8080/api/tickets/8f6f5d4e-1a7b-51d5-3f8a-4d6c8e0f2a4c" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```
- **Response**: 204 No Content on success, 404 if not found

## Event API

### Get All Events

- **Endpoint**: `GET /api/events`
- **Description**: Lists all available events
- **Auth Required**: Yes
- **Example Request**:
  ```bash
  curl -X GET "http://localhost:8080/api/events" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```
- **Response**: Array of event objects
- **Example Response**:
  ```json
  [
    {
      "id": "5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c",
      "name": "Summer Music Festival",
      "date": "2024-07-15T18:00:00Z",
      "venue": "Central Park",
      "ticketCount": 2
    },
    {
      "id": "7a1c4e8f-2b5d-49a3-8c6e-3f7b9d1f5a9c",
      "name": "Tech Conference 2024",
      "date": "2024-09-20T09:00:00Z",
      "venue": "Convention Center",
      "ticketCount": 2
    }
  ]
  ```

### Get Event by ID

- **Endpoint**: `GET /api/events/{id}`
- **Description**: Retrieves a specific event by its UUID
- **Auth Required**: Yes
- **Parameters**: 
  - `id` (path parameter) - Event UUID
- **Example Request**:
  ```bash
  curl -X GET "http://localhost:8080/api/events/5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```
- **Response**: Event details or 404 if not found

### Get Event Tickets

- **Endpoint**: `GET /api/events/{id}/tickets`
- **Description**: Lists all tickets for a specific event
- **Auth Required**: Yes
- **Parameters**: 
  - `id` (path parameter) - Event UUID
- **Example Request**:
  ```bash
  curl -X GET "http://localhost:8080/api/events/5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c/tickets" \
    -H "Authorization: Bearer YOUR_JWT_TOKEN"
  ```
- **Response**: Array of ticket objects or 404 if event not found
- **Example Response**:
  ```json
  [
    {
      "id": "8f6f5d4e-1a7b-51d5-3f8a-4d6c8e0f2a4c",
      "code": "SMF-001",
      "status": "ACTIVE",
      "event": {
        "id": "5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c",
        "name": "Summer Music Festival",
        "date": "2024-07-15T18:00:00Z"
      },
      "createdAt": "2024-01-15T10:00:00Z",
      "updatedAt": "2024-01-15T10:00:00Z"
    },
    {
      "id": "9g7h6e5f-3c8b-62e6-4g9b-5e8c9f2g6b0d",
      "code": "SMF-002",
      "status": "USED",
      "event": {
        "id": "5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c",
        "name": "Summer Music Festival",
        "date": "2024-07-15T18:00:00Z"
      },
      "createdAt": "2024-01-15T10:00:00Z",
      "updatedAt": "2024-05-20T16:45:00Z"
    }
  ]
  ```

## Health API

### Health Check

- **Endpoint**: `GET /api/health`
- **Description**: Checks if the service is running and healthy
- **Auth Required**: No
- **Example Request**:
  ```bash
  curl -X GET "http://localhost:8080/api/health"
  ```
- **Response**:
  ```json
  {
    "status": "UP",
    "message": "Ticket Management Service is running"
  }
  ```
- **HTTP Status Codes**:
  - 200 OK: Service is healthy
  - 500 Internal Server Error: Service is experiencing issues

## Data Models

### User

```json
{
  "id": "UUID",
  "username": "string",
  "roles": "comma-separated-roles",
  "createdAt": "DateTime",
  "updatedAt": "DateTime"
}
```

### Ticket

```json
{
  "id": "UUID",
  "code": "string",
  "status": "ACTIVE|USED",
  "event": {
    "id": "UUID",
    "name": "string",
    "date": "DateTime"
  },
  "createdAt": "DateTime",
  "updatedAt": "DateTime"
}
```

### Event

```json
{
  "id": "UUID",
  "name": "string",
  "date": "DateTime",
  "venue": "string",
  "ticketCount": "integer"
}
```

## Complete Workflow Examples

### Example 1: Login and Search for a Ticket

```bash
# Step 1: Login
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "admin", "password": "admin123"}')

# Step 2: Extract token (requires jq)
TOKEN=$(echo $LOGIN_RESPONSE | jq -r '.token')

# Step 3: Search for a ticket
curl -X GET "http://localhost:8080/api/tickets/search?code=SMF-001" \
  -H "Authorization: Bearer $TOKEN"
```

### Example 2: Update Ticket Status

```bash
# Using the token from above
curl -X PATCH "http://localhost:8080/api/tickets/8f6f5d4e-1a7b-51d5-3f8a-4d6c8e0f2a4c/status" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"status": "USED"}'
```

### Example 3: List All Events and Their Tickets

```bash
# Get all events
curl -X GET "http://localhost:8080/api/events" \
  -H "Authorization: Bearer $TOKEN"

# Get tickets for a specific event
curl -X GET "http://localhost:8080/api/events/5c3e2a1b-7d4f-48a2-9c5e-1a3b7d4f6a8c/tickets" \
  -H "Authorization: Bearer $TOKEN"
```

## Error Handling

All API endpoints return standard HTTP status codes:

- **200 OK**: Request successful
- **400 Bad Request**: Invalid request data or missing required fields
- **401 Unauthorized**: No valid authentication token provided
- **403 Forbidden**: Valid token but insufficient permissions
- **404 Not Found**: Requested resource does not exist
- **500 Internal Server Error**: Server-side error

### Error Response Format

All error responses follow a consistent JSON format:

```json
{
  "timestamp": "2024-05-26T14:30:00.123Z",
  "status": 404,
  "error": "Not Found",
  "message": "Ticket with code 'INVALID-001' not found",
  "path": "/api/tickets/search"
}
```

### Common Error Scenarios

#### Authentication Errors

```bash
# Missing token
curl -X GET "http://localhost:8080/api/events"
# Response: 401 Unauthorized

# Invalid token
curl -X GET "http://localhost:8080/api/events" \
  -H "Authorization: Bearer invalid-token"
# Response: 401 Unauthorized
```

#### Validation Errors

```bash
# Invalid ticket status
curl -X PUT "http://localhost:8080/api/tickets/8f6f5d4e-1a7b-51d5-3f8a-4d6c8e0f2a4c/status" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"status": "INVALID"}'
# Response: 400 Bad Request
```

## Rate Limiting

Currently, there are no rate limits enforced in the demo version. In a production environment, consider implementing rate limiting to prevent abuse.

## API Versioning

The current API version is v1. All endpoints are prefixed with `/api/` without explicit versioning. Future versions may include version numbers in the URL path.

## Additional Notes

- All timestamps are in ISO 8601 format (UTC)
- UUIDs are used for all entity identifiers
- Token expiration time varies based on configuration
- For production use, implement proper HTTPS and secure token storage
- Consider implementing API key authentication for service-to-service communication

## Testing Tools

### Postman Collection

A Postman collection is available for testing all endpoints. Import the collection and configure the following environment variables:
- `baseUrl`: `http://localhost:8080/api`
- `token`: Your JWT token after login

### cURL Scripts

For automation and testing, create shell scripts with the examples provided above. Remember to:
1. Store tokens securely
2. Handle token expiration
3. Implement proper error handling

## Support

For API-related questions or issues:
- Check the [FAQ](FAQ.md) for common questions
- Review the [Usage Guide](Usage.md) for implementation examples
- Consult the [Architecture documentation](Architecture.md) for system design details