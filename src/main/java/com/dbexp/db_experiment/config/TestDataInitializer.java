package com.dbexp.db_experiment.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dbexp.db_experiment.entity.User;
import com.dbexp.db_experiment.repository.UserRepository;

@Component
@Profile("dev")
@Order(1)
public class TestDataInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestDataInitializer.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TestDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(org.springframework.boot.ApplicationArguments args) throws Exception {
        logger.info("Initializing test data for development environment");

        // Create test users
        createTestUser("alice", "alice@example.com");
        createTestUser("bob", "bob@example.com");
        createTestUser("charlie", "charlie@example.com");
        createTestUser("diana", "diana@example.com");
        createTestUser("eve", "eve@example.com");

        logger.info("Test data initialization completed");
    }

    private void createTestUser(String username, String email) {
        // Encode the password
        String encodedPassword = passwordEncoder.encode("password123");

        // Try to find existing user by username
        userRepository.findByUsername(username).ifPresentOrElse(
                existingUser -> {
                    // Update existing user's password
                    existingUser.setPasswordHash(encodedPassword);
                    userRepository.save(existingUser);
                    logger.info("Updated password for existing user: {}", username);
                },
                () -> {
                    // Create new user if not found
                    User user = new User(username, encodedPassword, email);
                    userRepository.save(user);
                    logger.info("Created new test user: {}", username);
                });
    }
}