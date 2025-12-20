# 🚀 Enhanced PiggyBank System API Documentation

## 🔐 Authentication & Security Endpoints

### Register User
```http
POST /auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "password": "password123",
  "phoneNumber": "+1234567890",
  "locationId": 1
}
```

### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "refreshToken": "uuid-refresh-token",
  "type": "Bearer",
  "email": "john@example.com",
  "role": "USER",
  "requiresTwoFactor": false
}
```

### Refresh Token
```http
POST /auth/refresh
Content-Type: application/json

{
  "refreshToken": "uuid-refresh-token"
}
```

### Logout
```http
POST /auth/logout
Authorization: Bearer {token}
```

### Two-Factor Authentication

#### Send 2FA Code
```http
POST /auth/2fa/send?email=john@example.com
```

#### Verify 2FA Code
```http
POST /auth/2fa/verify
Content-Type: application/json

{
  "email": "john@example.com",
  "otp": "123456"
}
```

### Password Reset

#### Request Password Reset
```http
POST /auth/forgot-password?email=john@example.com
```

#### Reset Password
```http
POST /auth/reset-password
Content-Type: application/json

{
  "token": "reset-token-uuid",
  "newPassword": "newPassword123"
}
```

## 🔍 Global Search

### Search Across All Entities
```http
GET /search?query=john
Authorization: Bearer {token}
```

**Response:**
```json
[
  {
    "entityType": "USER",
    "id": 1,
    "displayLabel": "John Doe",
    "description": "john@example.com"
  },
  {
    "entityType": "SAVING_GOAL",
    "id": 5,
    "displayLabel": "John's Vacation Fund",
    "description": "Target: $5000"
  }
]
```

## 📊 Dashboard Analytics

### Get Dashboard Summary
```http
GET /dashboard/summary
Authorization: Bearer {token}
```

**Response:**
```json
{
  "totalUsers": 150,
  "totalSavingGoals": 300,
  "totalTransactions": 1200,
  "totalSavingsAmount": 45000.50,
  "categoryDistribution": {
    "Vacation": 45,
    "Emergency": 30,
    "Education": 25
  },
  "monthlyActivity": {
    "2024-01": 5000.00,
    "2024-02": 7500.00,
    "2024-03": 6200.00
  }
}
```

### Get Chart Data
```http
GET /dashboard/charts
Authorization: Bearer {token}
```

## 🎯 Enhanced Saving Goals with Search & Pagination

### Get All Saving Goals with Search
```http
GET /api/saving-goals?search=vacation&page=0&size=10&sort=createdAt&direction=desc
Authorization: Bearer {token}
```

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "goalName": "Vacation Fund",
      "description": "Summer vacation to Europe",
      "targetAmount": 5000.00,
      "currentAmount": 2500.00,
      "status": "ACTIVE"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10
  },
  "totalElements": 25,
  "totalPages": 3
}
```

## 🔒 OAuth2 Login

### Google Login
```http
GET /oauth2/authorization/google
```

### GitHub Login
```http
GET /oauth2/authorization/github
```

## 🛡️ Role-Based Access Control

### Roles:
- **USER**: Access to personal data only
- **MANAGER**: Access to dashboard analytics
- **ADMIN**: Full system access

### Protected Endpoints:
- `/dashboard/**` - Requires ADMIN or MANAGER role
- `/admin/**` - Requires ADMIN role
- All other endpoints - Requires authentication

## 📧 Email Features

The system automatically sends emails for:
- Welcome messages for new users
- Password reset links
- Two-factor authentication codes
- Account notifications

## 🌐 CORS Configuration

The API supports cross-origin requests from any domain with the following methods:
- GET, POST, PUT, DELETE, OPTIONS

## 🔧 Error Handling

All endpoints return consistent error responses:

```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "message": "Validation failed",
  "errors": {
    "email": "Email is required",
    "password": "Password must be at least 8 characters"
  }
}
```

## 🚀 Frontend Integration

### React/Axios Example:

```javascript
// Login
const login = async (email, password) => {
  const response = await axios.post('/auth/login', { email, password });
  localStorage.setItem('token', response.data.token);
  localStorage.setItem('refreshToken', response.data.refreshToken);
};

// API calls with token
const apiCall = async () => {
  const token = localStorage.getItem('token');
  const response = await axios.get('/api/saving-goals', {
    headers: { Authorization: `Bearer ${token}` }
  });
};

// Global search
const globalSearch = async (query) => {
  const response = await axios.get(`/search?query=${query}`, {
    headers: { Authorization: `Bearer ${token}` }
  });
};
```

## 📱 Production Ready Features

✅ JWT Authentication with Refresh Tokens  
✅ Two-Factor Authentication (Email-based)  
✅ OAuth2 Login (Google & GitHub)  
✅ Role-Based Authorization  
✅ Password Reset via Email  
✅ Global Search Functionality  
✅ Table Search with Pagination  
✅ Dashboard Analytics APIs  
✅ CORS Configuration  
✅ Global Exception Handling  
✅ Email Service Integration  
✅ Secure Token Management  

## 🔧 Configuration Required

Update `application.properties` with:
- Email SMTP settings
- OAuth2 client credentials
- JWT secret keys
- Database configuration

The system is now production-ready with comprehensive security, search, and analytics features!