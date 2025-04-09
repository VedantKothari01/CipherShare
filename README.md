# CipherShare: Decentralized Secure Cloud Storage

CipherShare is an ongoing project aimed at building a decentralized, secure cloud storage solution. The goal is to create a system that ensures data privacy, end-to-end encryption, and scalable storage by leveraging microservices architecture and modern cloud technologies.

## Project Overview

This project is in its early phases, focusing on building a robust and secure cloud storage system. The architecture is modular, using independent microservices for scalability and reliability. The system supports secure, decentralized storage and encrypted access for users, with each microservice handling a distinct domain (User, File, Sharing, and Audit).

## Current Status

- **Phase 1**: Completed the project synopsis, initial architecture planning, and database design along with the Entity-Relationship Diagram (ERD).
- **Phase 2**: Core microservices have been developed, including:
  - **User Service**: Manages user registration, authentication, and profile management.
  - **File Service**: Handles file metadata and versioning, with inter-service communication to log audit records.
  - **Sharing Service**: Manages file sharing permissions and access.
  - **Audit Service**: Records blockchain-style audit logs for file actions.

## Technologies to be Used

- **Microservices Architecture**: Java 23, Spring Boot, Spring Cloud
- **Database**: MySQL 8, Hibernate ORM (Spring Data JPA)
- **Containerization & Orchestration**: Docker, Kubernetes (planned for future phases)
- **API Communication**: RESTful APIs, OpenFeign (for inter-service calls)
- **Security**: Spring Security, OAuth 2.0, HTTPS (to be enhanced in future phases)
- **Deployment**: Jenkins, Helm (planned for future phases)
- **Monitoring**: Prometheus, Grafana (planned for future phases)
- **Version Control**: Git (GitHub)

## Project Phases

### Phase 1: Synopsis & ERD

- **Synopsis Document**: Contains the project’s problem statement, objectives, scope, technologies, and expected outcomes.
- **Entity-Relationship Diagram (ERD)**: A draft ERD defining key entities and relationships, with normalization practices applied.

### Phase 2: Development of Microservices

- **Core Microservices Implemented:**
  - User Service
  - File Service
  - Sharing Service
  - Audit Service
- **Implementation Details:**
  - Spring Data JPA/Hibernate is used for database interactions with MySQL.
  - Inter-service communication is enabled via OpenFeign.
  - Each microservice exposes its own REST endpoints, with Swagger API documentation provided through Springdoc OpenAPI.

## Next Steps

- Finalize microservices architecture and refine database design.
- Enhance inter-service communication and add more business logic.
- Dockerize the microservices and set up Kubernetes for orchestration in future phases.
- Implement additional components such as an API Gateway and Eureka Server.

## Future Enhancements

- Decentralized storage implementation
- Further refinement of microservices for file management
- Enhanced security with multi-factor authentication
- Real-time access and sharing of encrypted files
- API Gateway and Eureka Server for service discovery and unified entry point

## Contributors

- **Vedant Kothari**
- **Shaurya Patil**
- **Tanishq Nabar**
- **Aniruddha Gurjar**
- **Akshat Vora**
