package com.ciphershare.user.controller;

import com.ciphershare.user.entity.User;
import com.ciphershare.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "User API", description = "Manages user registration, authentication, and profile management")
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
        summary = "Register a new user", 
        description = "Creates a new user account",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "username": "testuser",
                      "email": "test@example.com",
                      "password": "testpass123",
                      "firstName": "Test",
                      "lastName": "User"
                    }
                    """
                )
            )
        )
    )
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @Operation(
        summary = "User login", 
        description = "Authenticates a user",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "username": "testuser",
                      "password": "testpass123"
                    }
                    """
                )
            )
        )
    )
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");
        userService.loginUser(username, password);
        return ResponseEntity.ok("Login successful");
    }

    @Operation(
        summary = "Get user by ID", 
        description = "Retrieves a user's details by their ID",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "User found",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = """
                        {
                          "userId": "123e4567-e89b-12d3-a456-426614174000",
                          "username": "testuser",
                          "email": "test@example.com",
                          "firstName": "Test",
                          "lastName": "User",
                          "role": "USER",
                          "isEnabled": true
                        }
                        """
                    )
                )
            )
        }
    )
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        Optional<User> user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Get all users", 
        description = "Retrieves a list of all users",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                description = "List of users",
                content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(
                        value = """
                        [
                          {
                            "userId": "123e4567-e89b-12d3-a456-426614174000",
                            "username": "testuser",
                            "email": "test@example.com",
                            "firstName": "Test",
                            "lastName": "User",
                            "role": "USER"
                          }
                        ]
                        """
                    )
                )
            )
        }
    )
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(
        summary = "Update user", 
        description = "Updates a user's details",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "firstName": "Updated",
                      "lastName": "User",
                      "email": "updated@example.com"
                    }
                    """
                )
            )
        )
    )
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @Operation(
        summary = "Delete user", 
        description = "Deletes a user by their ID"
    )
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
