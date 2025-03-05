# CipherShare Audit Service

**Purpose:**  
Manages blockchain-style immutable audit logs for file actions in CipherShare.

## Endpoints

- **POST** `/api/audit/record`  
  Adds a new audit record.

- **GET** `/api/audit/records/{fileId}`  
  Retrieves audit records for a given file ID.

## How to Run

1. Configure MySQL in `application.yml`.
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`


## API Documentation
Once the service is running, you can access the Swagger documentation at:
[Swagger UI - User Service](http://localhost:8080/swagger-ui.html)
