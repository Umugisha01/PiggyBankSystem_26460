# PiggyBank System API Documentation

## Overview
The PiggyBank System is a RESTful API for managing personal savings goals and financial transactions, organized by Rwanda's administrative hierarchy.

## Base URL
```
http://localhost:8080
```

## Authentication
All protected endpoints require JWT token in Authorization header:
```
Authorization: Bearer <jwt_token>
```

## API Endpoints

### Authentication
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/auth/register` | Register new user | No |
| POST | `/auth/login` | User login | No |

### User Management
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/users` | Get all users | Admin |
| GET | `/api/users/{id}` | Get user by ID | Yes |
| GET | `/api/users/location/province/{name}` | Users by province | Yes |
| GET | `/api/users/location/province/{name}/count` | Count users by province | Yes |

### Account Operations
| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/accounts/{id}/deposit?amount=X` | Deposit money | Yes |
| POST | `/api/accounts/{id}/withdraw?amount=X` | Withdraw money | Yes |
| GET | `/api/accounts/user/{userId}/balance` | Get user balance | Yes |
| GET | `/api/accounts/richest` | Get richest users | Admin |

## Request/Response Examples

### User Registration
```json
POST /auth/register
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "password123",
  "phoneNumber": "+250788123456",
  "locationId": 1
}

Response:
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "email": "john@example.com",
  "role": "USER"
}
```

### Deposit Money
```json
POST /api/accounts/1/deposit?amount=1000
Authorization: Bearer <token>

Response:
{
  "id": 1,
  "balance": 1000.0,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:30:00"
}
```

## Error Responses
```json
{
  "timestamp": "2024-01-01T10:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Insufficient balance"
}
```

## Status Codes
- 200: Success
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error