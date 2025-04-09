# CipherShare: Decentralized Secure Cloud Storage

A microservices-based secure file sharing platform with decentralized storage using IPFS.

## Architecture

- **User Service**: Handles user management and authentication
- **File Service**: Manages file storage with IPFS integration
- **Audit Service**: Tracks all file operations
- **Sharing Service**: Manages file sharing permissions
- **API Gateway**: Single entry point for all services
- **Eureka Server**: Service discovery
- **MySQL**: Database for all services

## Prerequisites

- Java 17+
- Maven
- Docker
- Docker Compose
- Kubernetes (optional)
- MySQL 8

## Quick Start

### Using Docker Compose

1. Clone the repository:
```bash
git clone https://github.com/VedantKothari01/CipherShare.git
cd CipherShare
```

2. Build and start all services:
```bash
docker-compose up -d
```

3. Access the services:
- API Gateway: http://localhost:8080
- Eureka Dashboard: http://localhost:8761
- Swagger UI for each service:
  - User Service: http://localhost:8084/swagger-ui.html
  - File Service: http://localhost:8081/swagger-ui.html
  - Audit Service: http://localhost:8082/swagger-ui.html
  - Sharing Service: http://localhost:8083/swagger-ui.html

### Using Kubernetes

1. Start Kubernetes cluster (Docker Desktop includes Kubernetes)

2. Apply all services:
```bash
kubectl apply -f k8s/mysql.yaml
kubectl apply -f k8s/discovery-service.yaml
kubectl apply -f k8s/user-service.yaml
kubectl apply -f k8s/file-service.yaml
kubectl apply -f k8s/audit-service.yaml
kubectl apply -f k8s/sharing-service.yaml
kubectl apply -f k8s/api-gateway.yaml
```

3. Access services through the API Gateway:
```bash
kubectl port-forward service/api-gateway 8080:8080
```

## Service Details

Each service has its own README with detailed information:
- [User Service](ciphershare-user-service/README.md)
- [File Service](ciphershare-file-service/README.md)
- [Audit Service](ciphershare-audit-service/README.md)
- [Sharing Service](ciphershare-sharing-service/README.md)

## API Documentation

All services provide Swagger UI documentation at their respective ports:
- User Service: 8084
- File Service: 8081
- Audit Service: 8082
- Sharing Service: 8083

## Development

### Local Development Setup

1. Start MySQL:
```bash
docker run -d --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=ciphershare mysql:8
```

2. Start Eureka Server:
```bash
cd ciphershare-discovery-service
mvn spring-boot:run
```

3. Start other services in any order:
```bash
cd ciphershare-user-service
mvn spring-boot:run

cd ciphershare-file-service
mvn spring-boot:run

cd ciphershare-audit-service
mvn spring-boot:run

cd ciphershare-sharing-service
mvn spring-boot:run

cd ciphershare-api-gateway
mvn spring-boot:run
```

## Configuration

All services use Spring Cloud Config to pull configuration from the central config server. Configuration files are stored in the `config-repo` directory.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
