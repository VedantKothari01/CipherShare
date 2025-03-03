package com.ciphershare.user.service;

import com.ciphershare.user.entity.User;
import com.ciphershare.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // In production, hash the password before saving
        return userRepository.save(user);
    }

    public User login(String email, String passwordHash) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getPasswordHash().equals(passwordHash))
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(String userId, User updatedUser) {
        User existing = getUserById(userId);
        existing.setUsername(updatedUser.getUsername());
        existing.setEmail(updatedUser.getEmail());
        existing.setPasswordHash(updatedUser.getPasswordHash());
        existing.setRole(updatedUser.getRole());
        existing.setPhoneNumber(updatedUser.getPhoneNumber());
        return userRepository.save(existing);
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
