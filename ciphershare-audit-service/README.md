# CipherShare Audit Service

## Purpose
Manages audit logs for all file operations and user activities, providing a secure and immutable record of actions.

## Features
- Audit log creation and retrieval
- Blockchain-style immutable logging
- Activity tracking
- Secure log storage
- Query and filter capabilities

## Endpoints

### Audit Management
- **POST** `/api/audit`
  - Creates a new audit log entry
  - Request body: Audit log details
  - Returns: Created audit log

- **GET** `/api/audit/{auditId}`
  - Retrieves a specific audit log
  - Returns: Audit log object

- **GET** `/api/audit`
  - Lists all audit logs
  - Optional query parameters for filtering
  - Returns: List of audit logs

- **GET** `/api/audit/user/{userId}`
  - Retrieves audit logs for a specific user
  - Returns: List of user's audit logs

- **GET** `/api/audit/file/{fileId}`
  - Retrieves audit logs for a specific file
  - Returns: List of file's audit logs

## How to Run

### Local Development
1. Ensure MySQL is running
2. Update `application.properties` with your DB credentials
3. Build: `mvn clean install`
4. Run: `mvn spring-boot:run`

### Docker
```bash
# Build the image
docker build -t ciphershare-audit-service .

# Run the container
docker run -p 8082:8082 ciphershare-audit-service
```

### Kubernetes
```bash
# Apply the deployment
kubectl apply -f k8s/audit-service.yaml
```

## API Documentation
Once the service is running, access the Swagger documentation at:
`http://localhost:8082/swagger-ui.html`

## Configuration
The service uses Spring Cloud Config to pull configuration from the central config server. Key configurations include:
- Server port: 8082
- Database connection
- Eureka service discovery

## Dependencies
- Spring Boot
- Spring Data JPA
- MySQL
- Eureka Client
- Spring Cloud Config Client
- SpringFox Swagger
