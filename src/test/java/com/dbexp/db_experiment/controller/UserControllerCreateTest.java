package com.dbexp.db_experiment.controller;

import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dbexp.db_experiment.dto.user.CreateUserRequest;
import com.dbexp.db_experiment.dto.user.CreateUserResponse;
import com.dbexp.db_experiment.service.UserService;
import com.dbexp.db_experiment.testutil.CreateUserRequestBuilder;
import com.dbexp.db_experiment.testutil.CreateUserResponseBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Controller - Create User Tests")
class UserControllerCreateTest {

    @Mock
    private UserService userService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        UserController userController = new UserController(userService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Nested
    @DisplayName("Successful Creation")
    class SuccessTests {

        @Test
        @DisplayName("Should create user successfully with valid data")
        void createUser_Success() throws Exception {
            CreateUserRequest request = CreateUserRequestBuilder.validRequest().build();
            CreateUserResponse response = CreateUserResponseBuilder.successResponse().build();

            when(userService.createUser(any(CreateUserRequest.class))).thenReturn(response);

            performCreateUserRequest(request)
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.userId").value(response.getUserId()))
                    .andExpect(jsonPath("$.username").value(response.getUsername()))
                    .andExpect(jsonPath("$.email").value(response.getEmail()))
                    .andExpect(jsonPath("$.createdAt").exists());
        }
    }

    @Nested
    @DisplayName("Validation Errors")
    class ValidationTests {

        @ParameterizedTest
        @MethodSource("invalidUsernameProvider")
        @DisplayName("Should return bad request for invalid username")
        void createUser_InvalidUsername_ReturnsBadRequest(String username) throws Exception {
            CreateUserRequest request = CreateUserRequestBuilder.validRequest()
                    .withUsername(username)
                    .build();

            performCreateUserRequest(request)
                    .andExpect(status().isBadRequest());
        }

        @ParameterizedTest
        @MethodSource("invalidEmailProvider")
        @DisplayName("Should return bad request for invalid email")
        void createUser_InvalidEmail_ReturnsBadRequest(String email) throws Exception {
            CreateUserRequest request = CreateUserRequestBuilder.validRequest()
                    .withEmail(email)
                    .build();

            performCreateUserRequest(request)
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return bad request for empty password")
        void createUser_EmptyPassword_ReturnsBadRequest() throws Exception {
            CreateUserRequest request = CreateUserRequestBuilder.validRequest()
                    .withPassword("")
                    .build();

            performCreateUserRequest(request)
                    .andExpect(status().isBadRequest());
        }

        static Stream<String> invalidUsernameProvider() {
            return Stream.of("", "ab", "a".repeat(256)); // Empty, too short, too long
        }

        static Stream<String> invalidEmailProvider() {
            return Stream.of("", "invalid", "@example.com");
        }
    }

    @Nested
    @DisplayName("Business Logic Errors")
    class BusinessLogicTests {

        @Test
        @DisplayName("Should return bad request when username already exists")
        void createUser_DuplicateUsername_ReturnsBadRequest() throws Exception {
            CreateUserRequest request = CreateUserRequestBuilder.validRequest().build();

            when(userService.createUser(any(CreateUserRequest.class)))
                    .thenThrow(new IllegalArgumentException("Username already exists"));

            performCreateUserRequest(request)
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("Username already exists"));
        }

        @Test
        @DisplayName("Should return bad request when email already exists")
        void createUser_DuplicateEmail_ReturnsBadRequest() throws Exception {
            CreateUserRequest request = CreateUserRequestBuilder.validRequest().build();

            when(userService.createUser(any(CreateUserRequest.class)))
                    .thenThrow(new IllegalArgumentException("Email already exists"));

            performCreateUserRequest(request)
                    .andExpect(status().isBadRequest())
                    .andExpect(content().string("Email already exists"));
        }
    }

    @Nested
    @DisplayName("Server Errors")
    class ServerErrorTests {

        @Test
        @DisplayName("Should return internal server error on unexpected exception")
        void createUser_InternalServerError() throws Exception {
            CreateUserRequest request = CreateUserRequestBuilder.validRequest().build();

            when(userService.createUser(any(CreateUserRequest.class)))
                    .thenThrow(new RuntimeException("Database connection failed"));

            performCreateUserRequest(request)
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().string("An error occurred while creating the user"));
        }
    }

    // Helper methods
    private ResultActions performCreateUserRequest(CreateUserRequest request) throws Exception {
        return mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
    }

    // Data providers - must be at class level for @MethodSource to work
    static Stream<String> invalidUsernameProvider() {
        return Stream.of("", "ab", "a".repeat(256), "user@name!", "user name");
    }

    static Stream<String> invalidEmailProvider() {
        return Stream.of("", "invalid-email", "invalid@", "@invalid.com", "invalid@");
    }
}