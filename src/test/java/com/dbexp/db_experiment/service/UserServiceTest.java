package com.dbexp.db_experiment.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dbexp.db_experiment.dto.user.CreateUserRequest;
import com.dbexp.db_experiment.dto.user.CreateUserResponse;
import com.dbexp.db_experiment.entity.User;
import com.dbexp.db_experiment.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Legacy UserServiceTest - Contains only user creation tests.
 * Other functionality has been extracted to focused test classes.
 *
 * @deprecated Use specific test classes for each operation:
 * - UserServiceCreateTest for user creation
 * - UserServiceUsernameTest for username changes
 * - UserServicePasswordTest for password changes
 * - UserServiceEmailTest for email changes
 * - UserServiceDeleteTest for account deletion
 */
@Deprecated
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @SuppressWarnings("null")
    @Test
    void createUser_Success() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest("testuser", "test@example.com", "password123");

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");

        User savedUser = new User("testuser", "hashedPassword", "test@example.com");
        savedUser.setUserId(1L);
        savedUser.setCreatedAt(LocalDateTime.now());

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        CreateUserResponse response = userService.createUser(request);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getUserId());
        assertEquals("testuser", response.getUsername());
        assertEquals("test@example.com", response.getEmail());
        assertNotNull(response.getCreatedAt());

        verify(userRepository).existsByUsername(request.getUsername());
        verify(userRepository).existsByEmail(request.getEmail());
        verify(passwordEncoder).encode(request.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @SuppressWarnings("null")
    @Test
    void createUser_DuplicateUsername_ThrowsException() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest("existinguser", "test@example.com", "password123");

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(request);
        });

        assertEquals("Username already exists", exception.getMessage());

        verify(userRepository).existsByUsername(request.getUsername());
        verify(userRepository, never()).existsByEmail(any());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }

    @SuppressWarnings("null")
    @Test
    void createUser_DuplicateEmail_ThrowsException() {
        // Arrange
        CreateUserRequest request = new CreateUserRequest("newuser", "existing@example.com", "password123");

        when(userRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(request.getEmail())).thenReturn(true);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(request);
        });

        assertEquals("Email already exists", exception.getMessage());

        verify(userRepository).existsByUsername(request.getUsername());
        verify(userRepository).existsByEmail(request.getEmail());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
    }
}