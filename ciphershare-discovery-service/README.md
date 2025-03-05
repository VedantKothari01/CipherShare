# CipherShare Discovery Service

**Purpose:**  
This module serves as a Eureka Discovery Server for the CipherShare platform. It allows all other microservices (User, File, Sharing, Audit) to register and discover each other without needing hardcoded URLs.

## Key Features
- **Service Registry**: Provides a registry where each microservice can register using Eureka.
- **Centralized Discovery**: Other services can dynamically locate each other by name.
- **Dashboard**: A web UI at [http://localhost:8761](http://localhost:8761) to view registered services.

## Configuration

### `application.properties`
```properties
spring.application.name=ciphershare-discovery-service
server.port=8761

eureka.client.register-with-eureka=false
eureka.client.fetch-registry=false
