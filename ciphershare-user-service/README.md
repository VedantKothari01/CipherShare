# CipherShare User Service

**Purpose:**  
Handles user registration, authentication, and profile management.


## Endpoints

- **POST** `/api/users/register`  
  Registers a new user.

- **POST** `/api/users/login?email={email}&passwordHash={passwordHash}`  
  Logs in a user.

- **GET** `/api/users/{userId}`  
  Retrieves user details by ID.

- **GET** `/api/users`  
  Retrieves all users.

- **PUT** `/api/users/{userId}`  
  Updates a user.

- **DELETE** `/api/users/{userId}`  
  Deletes a user.

## How to Run

1. Ensure MySQL is running and update the `application.yml` with your DB credentials.
2. Build: `mvn clean install`
3. Run: `mvn spring-boot:run`


## API Documentation
Once the service is running, you can access the Swagger documentation at:
[Swagger UI - User Service](http://localhost:8080/swagger-ui.html)
