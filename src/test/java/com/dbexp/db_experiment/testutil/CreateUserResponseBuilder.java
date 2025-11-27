package com.dbexp.db_experiment.testutil;

import java.time.LocalDateTime;

import com.dbexp.db_experiment.dto.user.CreateUserResponse;

public class CreateUserResponseBuilder {

    private Long userId = 1L;
    private String username = "testuser";
    private String email = "test@example.com";
    private LocalDateTime createdAt = LocalDateTime.now();

    public static CreateUserResponseBuilder successResponse() {
        return new CreateUserResponseBuilder();
    }

    public CreateUserResponseBuilder withUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public CreateUserResponseBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public CreateUserResponseBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CreateUserResponseBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public CreateUserResponse build() {
        return new CreateUserResponse(userId, username, email, createdAt);
    }
}