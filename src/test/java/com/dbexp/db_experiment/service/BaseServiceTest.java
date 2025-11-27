package com.dbexp.db_experiment.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dbexp.db_experiment.entity.User;
import com.dbexp.db_experiment.repository.UserRepository;
import com.dbexp.db_experiment.testutil.UserTestBuilder;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public abstract class BaseServiceTest {

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected PasswordEncoder passwordEncoder;

    protected UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, passwordEncoder);
    }

    protected User createMockUser(Long userId, String username, String email, String password) {
        User user = UserTestBuilder.aUser()
                .withUserId(userId)
                .withUsername(username)
                .withEmail(email)
                .withPassword(password)
                .build();
        return user;
    }

    protected void mockUserRepositoryFindById(Long userId, User user) {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
    }

    protected void mockUserRepositoryFindByIdNotFound(Long userId) {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
    }

    protected void mockPasswordEncoderMatches(String rawPassword, String encodedPassword, boolean result) {
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(result);
    }

    protected void mockPasswordEncoderEncode(String rawPassword, String encodedPassword) {
        when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
    }

    protected void mockUsernameExists(String username, boolean exists) {
        when(userRepository.existsByUsername(username)).thenReturn(exists);
    }

    protected void mockEmailExists(String email, boolean exists) {
        when(userRepository.existsByEmail(email)).thenReturn(exists);
    }

    protected void mockUsernameUpdate(Long userId, String newUsername, int affectedRows) {
        when(userRepository.updateUsername(userId, newUsername)).thenReturn(affectedRows);
    }

    protected void mockPasswordUpdate(Long userId, String newPassword, int affectedRows) {
        when(userRepository.updatePassword(userId, newPassword)).thenReturn(affectedRows);
    }

    protected void mockEmailUpdate(Long userId, String newEmail, int affectedRows) {
        when(userRepository.updateEmail(userId, newEmail)).thenReturn(affectedRows);
    }

    protected void mockUserDelete(Long userId, int affectedRows) {
        when(userRepository.deleteByUserId(userId)).thenReturn(affectedRows);
    }
}