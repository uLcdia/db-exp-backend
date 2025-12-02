package com.dbexp.db_experiment.controller;

import java.time.LocalDateTime;

import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dbexp.db_experiment.dto.user.GetUserByIdRequest;
import com.dbexp.db_experiment.dto.user.GetUserByIdResponse;
import com.dbexp.db_experiment.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@DisplayName("User Controller - Get User By ID Tests")
class UserControllerGetUserByIdTest extends BaseControllerTest {

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        UserController userController = new UserController(userService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Nested
    @DisplayName("Successful User Retrievals by ID")
    class SuccessTests {

        @Test
        @DisplayName("Should retrieve user successfully with valid ID")
        void getUserById_Success() throws Exception {
            Long userId = 1L;
            GetUserByIdRequest request = new GetUserByIdRequest(userId);
            GetUserByIdResponse response = new GetUserByIdResponse(userId, "testname", "test@example.com",
                    LocalDateTime.now());

            when(userService.getUserById(any(GetUserByIdRequest.class))).thenReturn(response);

            performGetRequest("/api/users/{userId}", userId, request)
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.userId").value(userId))
                    .andExpect(jsonPath("$.username").value("testname"))
                    .andExpect(jsonPath("$.email").value("test@example.com"))
                    .andExpect(jsonPath("$.creationTime").exists());
        }
    }

    @Nested
    @DisplayName("Business Logic Error Tests")
    class BusinessLogicErrorTests {

        @Test
        @DisplayName("Should return bad request when user not found")
        void getUserById_InvalidUserId() throws Exception {
            Long userId = 999L;
            GetUserByIdRequest request = new GetUserByIdRequest(userId);

            when(userService.getUserById(any(GetUserByIdRequest.class)))
                    .thenThrow(new IllegalArgumentException("User not found"));

            assertBadRequestWithMessage(performGetRequest("/api/users/{userId}", userId, request), "User not found");
        }
    }

    // Helper method to handle URL path variables
    private ResultActions performGetRequest(String url, Long userId, Object request) throws Exception {
        String formattedUrl = url.replace("{userId}", String.valueOf(userId));
        return performGetRequest(formattedUrl, request);
    }
}
