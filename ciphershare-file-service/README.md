# CipherShare File Service

**Purpose:**  
Handles file metadata and versioning for CipherShare. Demonstrates inter-service communication by logging an audit record via the Audit Service upon file creation.

## Endpoints

- **POST** `/api/files`  
  Creates a new file record and logs an audit record.

- **GET** `/api/files/{fileId}`  
  Retrieves details of a file.

- **PUT** `/api/files/{fileId}`  
  Updates a file's details.

- **DELETE** `/api/files/{fileId}`  
  Deletes a file record.

- **POST** `/api/files/versions`  
  Adds a new file version.

- **GET** `/api/files/versions/{fileId}`  
  Retrieves file version history.

## How to Run

1. Configure MySQL in `application.yml`.
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`
