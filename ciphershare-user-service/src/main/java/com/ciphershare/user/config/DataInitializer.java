package com.ciphershare.user.config;

import com.ciphershare.user.entity.User;
import com.ciphershare.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@ciphershare.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);
        }

        if (!userRepository.existsByUsername("testuser")) {
            User testUser = new User();
            testUser.setUsername("testuser");
            testUser.setEmail("test@ciphershare.com");
            testUser.setPassword(passwordEncoder.encode("test123"));
            testUser.setFirstName("Test");
            testUser.setLastName("User");
            testUser.setRole(User.Role.USER);
            userRepository.save(testUser);
        }

        if (!userRepository.existsByUsername("premium")) {
            User premiumUser = new User();
            premiumUser.setUsername("premium");
            premiumUser.setEmail("premium@ciphershare.com");
            premiumUser.setPassword(passwordEncoder.encode("premium123"));
            premiumUser.setFirstName("Premium");
            premiumUser.setLastName("User");
            premiumUser.setRole(User.Role.PREMIUM);
            userRepository.save(premiumUser);
        }
    }
}