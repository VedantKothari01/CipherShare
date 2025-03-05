package com.ciphershare.user.controller;

import com.ciphershare.user.entity.User;
import com.ciphershare.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints to manage users - registration, login, fetch, update, and delete users.")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Takes user details and registers a new account.")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Login using email and password hash.")
    public ResponseEntity<User> login(
            @Parameter(description = "User's email address") @RequestParam String email,
            @Parameter(description = "User's password hash") @RequestParam String passwordHash) {
        return ResponseEntity.ok(userService.login(email, passwordHash));
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Fetch a user by their unique ID.")
    public ResponseEntity<User> getUser(
            @Parameter(description = "Unique ID of the user") @PathVariable String userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users.")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user", description = "Update details of an existing user.")
    public ResponseEntity<User> updateUser(
            @Parameter(description = "Unique ID of the user") @PathVariable String userId,
            @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(userId, user));
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user", description = "Delete a user by their ID.")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "Unique ID of the user") @PathVariable String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
