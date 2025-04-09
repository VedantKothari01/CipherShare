# CipherShare File Service

## Purpose
Handles file upload, storage, and management with IPFS integration for decentralized storage.

## Features
- File upload and download
- IPFS integration for decentralized storage
- File metadata management
- Version control
- Secure file handling

## Endpoints

### File Management
- **POST** `/api/files/upload`
  - Uploads a new file
  - Request body: Multipart file
  - Returns: File metadata with IPFS hash

- **GET** `/api/files/{fileId}`
  - Retrieves file metadata
  - Returns: File metadata object

- **GET** `/api/files/{fileId}/download`
  - Downloads a file
  - Returns: File content

- **DELETE** `/api/files/{fileId}`
  - Deletes a file
  - Returns: 200 OK

- **GET** `/api/files`
  - Lists all files
  - Returns: List of file metadata

## How to Run

### Local Development
1. Ensure MySQL is running
2. Update `application.properties` with your DB credentials
3. Build: `mvn clean install`
4. Run: `mvn spring-boot:run`

### Docker
```bash
# Build the image
docker build -t ciphershare-file-service .

# Run the container
docker run -p 8081:8081 ciphershare-file-service
```

### Kubernetes
```bash
# Apply the deployment
kubectl apply -f k8s/file-service.yaml
```

## API Documentation
Once the service is running, access the Swagger documentation at:
`http://localhost:8081/swagger-ui.html`

## Configuration
The service uses Spring Cloud Config to pull configuration from the central config server. Key configurations include:
- Server port: 8081
- Database connection
- IPFS Pinata API credentials
- Eureka service discovery

## Dependencies
- Spring Boot
- Spring Data JPA
- MySQL
- IPFS Java Client
- Eureka Client
- Spring Cloud Config Client
- SpringFox Swagger
