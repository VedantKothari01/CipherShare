# CipherShare User Service

## Purpose
Handles user registration, authentication, and profile management with JWT-based security.

## Features
- User registration and authentication
- JWT token generation and validation
- Role-based access control (USER, ADMIN)
- Profile management
- Secure password handling

## Endpoints

### User Registration
- **POST** `/api/users/register`
  - Registers a new user
  - Request body: User details (username, email, password)
  - Returns: Created user object

### User Authentication
- **POST** `/api/users/login`
  - Authenticates a user
  - Request body: { "username": "string", "password": "string" }
  - Returns: JWT token

### User Management
- **GET** `/api/users/{userId}`
  - Retrieves user details by ID
  - Requires: USER role
  - Returns: User object

- **PUT** `/api/users/{userId}`
  - Updates user profile
  - Requires: USER role
  - Request body: Updated user details
  - Returns: Updated user object

- **DELETE** `/api/users/{userId}`
  - Deletes a user
  - Requires: ADMIN role
  - Returns: 200 OK

- **GET** `/api/users`
  - Retrieves all users
  - Requires: ADMIN role
  - Returns: List of users

## How to Run

### Local Development
1. Ensure MySQL is running
2. Update `application.properties` with your DB credentials
3. Build: `mvn clean install`
4. Run: `mvn spring-boot:run`

### Docker
```bash
# Build the image
docker build -t ciphershare-user-service .

# Run the container
docker run -p 8084:8084 ciphershare-user-service
```

### Kubernetes
```bash
# Apply the deployment
kubectl apply -f k8s/user-service-deployment.yaml
```

## API Documentation
Once the service is running, access the Swagger documentation at:
- Swagger UI: `http://localhost:8084/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8084/v3/api-docs`

## Configuration
The service uses Spring Cloud Config to pull configuration from the central config server. Key configurations include:
- Server port: 8084
- Database connection
- JWT secret and expiration
- Eureka service discovery

## Dependencies
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- Eureka Client
- Spring Cloud Config Client
- SpringDoc OpenAPI
