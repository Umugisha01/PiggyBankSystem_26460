# PiggyBank System UML Diagrams

## Class Diagram

### Core Entities
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│      User       │    │    Location     │    │ SavingsAccount  │
├─────────────────┤    ├─────────────────┤    ├─────────────────┤
│ - id: Long      │    │ - id: Long      │    │ - id: Long      │
│ - firstName     │    │ - name: String  │    │ - balance       │
│ - lastName      │    │ - code: String  │    │ - createdAt     │
│ - email         │    │ - type: Enum    │    │ - updatedAt     │
│ - password      │    │ - parent        │    │ - user: User    │
│ - role: Enum    │    │ - children      │    └─────────────────┘
│ - location      │    │ - users         │           │
│ - savingsAccount│    └─────────────────┘           │
└─────────────────┘           │                      │
         │                    │                      │
         │ @ManyToOne         │ @OneToMany           │ @OneToOne
         └────────────────────┘                      │
                                                     │
┌─────────────────┐    ┌─────────────────┐          │
│   Transaction   │    │   SavingGoal    │          │
├─────────────────┤    ├─────────────────┤          │
│ - id: Long      │    │ - id: Long      │          │
│ - amount        │    │ - name: String  │          │
│ - type: Enum    │    │ - targetAmount  │          │
│ - description   │    │ - currentAmount │          │
│ - date          │    │ - targetDate    │          │
│ - user: User    │    │ - status: Enum  │          │
│ - savingGoal    │    │ - user: User    │          │
└─────────────────┘    └─────────────────┘          │
         │                       │                  │
         │ @ManyToOne            │ @ManyToOne       │
         └───────────────────────┘                  │
                                                    │
┌─────────────────┐    ┌─────────────────┐          │
│    Category     │    │  UserCategory   │          │
├─────────────────┤    ├─────────────────┤          │
│ - id: Long      │    │ - id: Long      │          │
│ - name: String  │    │ - user: User    │          │
│ - description   │    │ - category      │          │
└─────────────────┘    └─────────────────┘          │
         │                       │                  │
         │ @OneToMany            │ @ManyToOne       │
         └───────────────────────┘                  │
                                                    │
                    @OneToOne                       │
                    ────────────────────────────────┘
```

## Sequence Diagram - User Registration

```
User -> AuthController: POST /auth/register
AuthController -> AuthService: register(request)
AuthService -> UserRepository: existsByEmail()
UserRepository -> AuthService: false
AuthService -> PasswordEncoder: encode(password)
PasswordEncoder -> AuthService: encodedPassword
AuthService -> UserRepository: save(user)
UserRepository -> AuthService: savedUser
AuthService -> SavingsAccountRepository: save(account)
SavingsAccountRepository -> AuthService: savedAccount
AuthService -> AuthenticationManager: authenticate()
AuthenticationManager -> AuthService: authentication
AuthService -> JwtTokenProvider: generateToken()
JwtTokenProvider -> AuthService: jwtToken
AuthService -> AuthController: AuthenticationResponse
AuthController -> User: 200 OK + token
```

## Component Diagram

```
┌─────────────────────────────────────────────────────────┐
│                    PiggyBank System                     │
├─────────────────────────────────────────────────────────┤
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐     │
│  │ Controllers │  │   Security  │  │     DTOs    │     │
│  │             │  │             │  │             │     │
│  │ - Auth      │  │ - JWT       │  │ - Request   │     │
│  │ - User      │  │ - Filter    │  │ - Response  │     │
│  │ - Account   │  │ - Config    │  │ - UserDto   │     │
│  └─────────────┘  └─────────────┘  └─────────────┘     │
│         │                 │                 │          │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐     │
│  │  Services   │  │ Repositories│  │   Models    │     │
│  │             │  │             │  │             │     │
│  │ - Auth      │  │ - User      │  │ - User      │     │
│  │ - User      │  │ - Account   │  │ - Location  │     │
│  │ - Account   │  │ - Location  │  │ - Account   │     │
│  └─────────────┘  └─────────────┘  └─────────────┘     │
│         │                 │                 │          │
│         └─────────────────┼─────────────────┘          │
│                           │                            │
│  ┌─────────────────────────────────────────────────────┤
│  │              PostgreSQL Database                    │
│  └─────────────────────────────────────────────────────┘
└─────────────────────────────────────────────────────────┘
```