# CipherShare: Decentralized Secure Cloud Storage

CipherShare is a secure file sharing platform built using microservices architecture. It provides secure file storage, sharing, and audit logging capabilities with decentralized storage using IPFS.

## Project Overview

This project implements a robust and secure cloud storage system with a modular architecture using independent microservices for scalability and reliability. The system supports secure, decentralized storage and encrypted access for users, with each microservice handling a distinct domain (User, File, Sharing, and Audit).

## Current Status

- **Phase 1**: Completed the project synopsis, initial architecture planning, and database design along with the Entity-Relationship Diagram (ERD).
- **Phase 2**: Core microservices have been developed, including:
  - **User Service**: Manages user registration, authentication, and profile management.
  - **File Service**: Handles file metadata and versioning, with inter-service communication to log audit records.
  - **Sharing Service**: Manages file sharing permissions and access.
  - **Audit Service**: Records blockchain-style audit logs for file actions.
- **Phase 3**: Infrastructure and Deployment
  - Docker containerization
  - Kubernetes orchestration
  - API Gateway implementation
  - Service discovery with Eureka
- **Phase 4**: Security and Monitoring
  - JWT-based authentication
  - IPFS integration for decentralized storage
  - Audit logging system
  - Environment-based configuration

## Architecture

- **User Service**: Handles user management and authentication
- **File Service**: Manages file storage with IPFS integration
- **Audit Service**: Tracks all file operations
- **Sharing Service**: Manages file sharing permissions
- **API Gateway**: Single entry point for all services
- **Eureka Server**: Service discovery
- **MySQL**: Database for all services

## Technologies Used

- **Microservices Architecture**: Java 17, Spring Boot, Spring Cloud
- **Database**: MySQL 8, Hibernate ORM (Spring Data JPA)
- **Containerization & Orchestration**: Docker, Kubernetes
- **API Communication**: RESTful APIs, OpenFeign
- **Security**: Spring Security, JWT, HTTPS
- **Deployment**: Docker Compose, Kubernetes
- **Monitoring**: Spring Boot Actuator
- **Version Control**: Git (GitHub)
- **Storage**: IPFS (via Pinata)

## Quick Start

### Prerequisites

- Java 17+
- Maven
- Docker
- Docker Compose
- Kubernetes (optional)
- MySQL 8

### Using Docker Compose

1. Clone the repository:
```bash
git clone https://github.com/VedantKothari01/CipherShare.git
cd CipherShare
```

2. Create a `.env` file with your configuration (see `.env.example` for template)

3. Build and start all services:
```bash
docker-compose up -d --build
```

4. Access the services:
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

## Development Setup

### Local Development

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

## API Documentation

All services provide Swagger UI documentation at their respective ports:
- User Service: 8084
- File Service: 8081
- Audit Service: 8082
- Sharing Service: 8083

## Future Enhancements

- Enhanced security with multi-factor authentication
- Real-time file sharing notifications
- Advanced file versioning
- Distributed tracing with Zipkin
- Prometheus and Grafana integration for monitoring

## Contributors

- **Vedant Kothari**
- **Shaurya Patil**
- **Tanishq Nabar**
- **Aniruddha Gurjar**
- **Akshat Vora**

## License

This project is licensed under the MIT License - see the LICENSE file for details.
