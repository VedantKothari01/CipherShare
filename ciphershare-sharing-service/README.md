# CipherShare Sharing Service

**Purpose:**  
Manages file sharing permissions, statuses, and access expiry for CipherShare.

## Endpoints

- **POST** `/api/sharing`  
  Shares a file by creating a share record.

- **GET** `/api/sharing/user/{userId}`  
  Retrieves all share records for a specified user.

- **PUT** `/api/sharing/{shareID}`  
  Updates sharing details (permissions, status, expiry).

- **DELETE** `/api/sharing/{shareID}`  
  Revokes a file share.

## How to Run

1. Configure MySQL in `application.yml`.
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`


## API Documentation
Once the service is running, you can access the Swagger documentation at:
[Swagger UI - User Service](http://localhost:8080/swagger-ui.html)
