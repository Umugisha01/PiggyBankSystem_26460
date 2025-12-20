# 🐷 PiggyBank System

The **PiggyBank System** is a comprehensive financial and savings management application built with **Java 17**, **Spring Boot (Maven)**, and **PostgreSQL**. It provides secure user authentication, savings goal management, and financial transaction tracking organized by **Rwanda's administrative hierarchy**.

---

## 📘 Project Description

This fintech system empowers users to:
- **Register and authenticate** securely with JWT tokens and OAuth2 (Google/GitHub)
- **Create and manage** personal savings goals and financial targets
- **Track financial transactions** including deposits, withdrawals, and transfers
- **Organize data geographically** using Rwanda's complete administrative structure
- **Access comprehensive APIs** with pagination, filtering, and search capabilities
- **Monitor progress** through dashboard analytics and reporting

The system implements complete **CRUD operations** for all entities with **One-to-Many**, **Many-to-Many**, and **Self-Referencing** relationships through **JPA/Hibernate**.

---

## 🏗️ Architecture & Technology Stack

### Backend Technologies
- **Java 17** - Programming language
- **Spring Boot 2.7.18** - Application framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Data persistence
- **PostgreSQL** - Primary database
- **JWT** - Token-based authentication
- **OAuth2** - Social login integration
- **Maven** - Dependency management
- **SpringDoc OpenAPI** - API documentation

### Security Features
- JWT token authentication
- OAuth2 integration (Google & GitHub)
- Password encryption with BCrypt
- Two-factor authentication (2FA)
- Password reset functionality
- Role-based access control (USER, ADMIN, MANAGER)

---

## 🧩 Core Entities

| Entity | Description | Key Features |
|--------|-------------|-------------|
| **User** | System users with authentication | JWT auth, OAuth2, 2FA, roles |
| **Location** | Rwanda's administrative hierarchy | Self-referencing structure |
| **SavingsAccount** | User financial accounts | Balance tracking, transactions |
| **SavingGoal** | User savings targets | Goal tracking, progress monitoring |
| **Transaction** | Financial operations | Deposits, withdrawals, transfers |
| **Category** | Savings categorization | Many-to-many with users |
| **RefreshToken** | JWT token management | Secure token refresh |

---

## 🔗 Database Relationships

| Relationship Type | Implementation | Examples |
|-------------------|----------------|----------|
| **One-to-Many** | `@OneToMany` mappings | User → Transactions, Location → Users |
| **Many-to-Many** | Junction tables | User ↔ Category |
| **Self-Referencing** | Hierarchical structure | Location → Parent Location |
| **One-to-One** | Direct mapping | User → SavingsAccount |

---

## 🚀 Getting Started

### Prerequisites
- **Java 17** or higher
- **PostgreSQL 12+**
- **Maven 3.6+**
- **Git**

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/Umugisha01/PiggyBankSystem_26460.git
   cd PiggyBankSystem_26460
   ```

2. **Setup PostgreSQL Database**
   ```sql
   CREATE DATABASE piggybankdb;
   CREATE USER postgres WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE piggybankdb TO postgres;
   ```

3. **Configure Application Properties**
   ```properties
   # Database Configuration
   spring.datasource.url=jdbc:postgresql://localhost:5432/piggybankdb
   spring.datasource.username=postgres
   spring.datasource.password=your_password
   
   # OAuth2 Configuration (Optional)
   GOOGLE_CLIENT_ID=your_google_client_id
   GOOGLE_CLIENT_SECRET=your_google_client_secret
   GITHUB_CLIENT_ID=your_github_client_id
   GITHUB_CLIENT_SECRET=your_github_client_secret
   ```

4. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

5. **Access the Application**
   - API Base URL: `http://localhost:8080`
   - Swagger UI: `http://localhost:8080/swagger-ui.html`

---

## 📚 API Documentation

### Authentication Endpoints
- `POST /auth/register` - User registration
- `POST /auth/login` - User login
- `POST /auth/refresh` - Token refresh
- `POST /auth/logout` - User logout
- `POST /auth/forgot-password` - Password reset request
- `POST /auth/reset-password` - Password reset
- `GET /oauth2/authorization/google` - Google OAuth2 login
- `GET /oauth2/authorization/github` - GitHub OAuth2 login

### User Management
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/role/{role}` - Get users by role
- `GET /api/users/location/{type}/{name}` - Get users by location

### Account Management
- `POST /api/accounts/{id}/deposit` - Deposit funds
- `POST /api/accounts/{id}/withdraw` - Withdraw funds
- `GET /api/accounts/user/{userId}` - Get user account
- `GET /api/accounts/user/{userId}/balance` - Get account balance

### Savings Goals
- `POST /api/goals` - Create savings goal
- `GET /api/goals/user/{userId}` - Get user goals
- `PUT /api/goals/{id}` - Update goal
- `DELETE /api/goals/{id}` - Delete goal

### Transactions
- `GET /api/transactions/user/{userId}` - Get user transactions
- `GET /api/transactions/account/{accountId}` - Get account transactions
- `POST /api/transactions` - Create transaction

### Location Services
- `GET /api/locations` - Get all locations
- `GET /api/locations/type/{type}` - Get locations by type
- `GET /api/locations/{id}/children` - Get child locations

### Admin Dashboard
- `GET /api/dashboard/summary` - System summary
- `GET /api/dashboard/users/stats` - User statistics
- `GET /api/dashboard/transactions/stats` - Transaction analytics

---

## 🔐 Authentication & Security

### JWT Authentication
```bash
# Login to get JWT token
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password"}'

# Use token in subsequent requests
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### OAuth2 Social Login
- **Google Login**: `GET /oauth2/authorization/google`
- **GitHub Login**: `GET /oauth2/authorization/github`

### Role-Based Access
- **USER**: Basic account operations
- **ADMIN**: Full system access, user management
- **MANAGER**: Advanced reporting and analytics

---

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### API Testing
Use the provided Postman collection: `PiggyBank_API_Collection.json`

Or test with curl:
```bash
# Register new user
curl -X POST http://localhost:8080/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "password": "SecurePass123!",
    "phoneNumber": "+250780123456"
  }'
```

---

## 📊 Database Schema

### Rwanda Location Hierarchy
```
Province (5 provinces)
├── District (30 districts)
    ├── Sector (416 sectors)
        ├── Cell (2,148 cells)
            └── Village (14,837 villages)
```

### Key Tables
- `users` - User accounts and authentication
- `locations` - Rwanda administrative hierarchy
- `savings_accounts` - User financial accounts
- `saving_goals` - User savings targets
- `transactions` - Financial operations
- `categories` - Savings categorization
- `refresh_tokens` - JWT token management

---

## 📸 API Screenshots

API demonstration screenshots are available in the `Photos/` directory:

| Operation | Screenshot |
|-----------|------------|
| User Registration | `Photos/CREATE_New_User.png` |
| Get User by Email | `Photos/GET_User_by_Email.png` |
| Update User | `Photos/UPDATE_User.png` |
| Delete User | `Photos/DELETE_User.png` |
| Location Pagination | `Photos/GET_Pagination.png` |
| System ERD | `Photos/PiggyBank.drawio.png` |

---

## 🔧 Configuration

### Environment Variables
```bash
# Database
DB_URL=jdbc:postgresql://localhost:5432/piggybankdb
DB_USERNAME=postgres
DB_PASSWORD=your_password

# JWT
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=86400000

# OAuth2
GOOGLE_CLIENT_ID=your_google_client_id
GOOGLE_CLIENT_SECRET=your_google_client_secret
GITHUB_CLIENT_ID=your_github_client_id
GITHUB_CLIENT_SECRET=your_github_client_secret

# Email (Optional)
MAIL_USERNAME=your_email@gmail.com
MAIL_PASSWORD=your_app_password
```

---

## 🚀 Deployment

### Docker Deployment (Optional)
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/PiggyBankSystem01-1.0.0.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

### Production Considerations
- Use environment-specific configuration files
- Enable HTTPS in production
- Configure proper CORS settings
- Set up database connection pooling
- Implement proper logging and monitoring

---

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📝 Additional Documentation

- [API Documentation](API_DOCUMENTATION.md)
- [Enhanced API Guide](ENHANCED_API_DOCUMENTATION.md)
- [Security Recommendations](SECURITY_RECOMMENDATIONS.md)
- [UML Diagram Description](UML_DIAGRAM_DESCRIPTION.md)

---

## 🐛 Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Verify PostgreSQL is running
   - Check database credentials in `application.properties`

2. **OAuth2 Login Issues**
   - Ensure OAuth2 credentials are properly configured
   - Check redirect URIs in OAuth2 provider settings

3. **JWT Token Errors**
   - Verify JWT secret key configuration
   - Check token expiration settings

---

## ⚖️ License

This project is licensed under the **MIT License** - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Umugisha Christian**
- GitHub: [@Umugisha01](https://github.com/Umugisha01)
- Email: umugishaone@gmail.com

---

## 🙏 Acknowledgments

- Spring Boot community for excellent documentation
- Rwanda's administrative structure for location hierarchy
- Contributors and testers who helped improve the system

---

> _"PiggyBank System — Empowering financial growth through smart savings management and secure technology."_