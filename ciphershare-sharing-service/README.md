# CipherShare Sharing Service

## Purpose
Manages file sharing permissions and access control between users, ensuring secure and controlled file distribution.

## Features
- File sharing between users
- Permission management
- Access control
- Share revocation
- Share history tracking

## Endpoints

### Share Management
- **POST** `/api/shares`
  - Creates a new file share
  - Request body: Share details (fileId, recipientId, permissions)
  - Returns: Created share object

- **GET** `/api/shares/{shareId}`
  - Retrieves share details
  - Returns: Share object

- **GET** `/api/shares`
  - Lists all shares
  - Optional query parameters for filtering
  - Returns: List of shares

- **DELETE** `/api/shares/{shareId}`
  - Revokes a share
  - Returns: 200 OK

- **GET** `/api/shares/user/{userId}`
  - Lists shares for a specific user
  - Returns: List of user's shares

- **GET** `/api/shares/file/{fileId}`
  - Lists shares for a specific file
  - Returns: List of file's shares

## How to Run

### Local Development
1. Ensure MySQL is running
2. Update `application.properties` with your DB credentials
3. Build: `mvn clean install`
4. Run: `mvn spring-boot:run`

### Docker
```bash
# Build the image
docker build -t ciphershare-sharing-service .

# Run the container
docker run -p 8083:8083 ciphershare-sharing-service
```

### Kubernetes
```bash
# Apply the deployment
kubectl apply -f k8s/sharing-service.yaml
```

## API Documentation
Once the service is running, access the Swagger documentation at:
`http://localhost:8083/swagger-ui.html`

## Configuration
The service uses Spring Cloud Config to pull configuration from the central config server. Key configurations include:
- Server port: 8083
- Database connection
- Eureka service discovery

## Dependencies
- Spring Boot
- Spring Data JPA
- MySQL
- Eureka Client
- Spring Cloud Config Client
- SpringFox Swagger
