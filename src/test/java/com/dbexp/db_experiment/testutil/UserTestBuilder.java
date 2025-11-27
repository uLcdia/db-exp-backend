package com.dbexp.db_experiment.testutil;

import java.time.LocalDateTime;

import com.dbexp.db_experiment.entity.User;

public class UserTestBuilder {

    private Long userId = 1L;
    private String username = "testuser";
    private String email = "test@example.com";
    private String password = "hashedPassword";
    private LocalDateTime createdAt = LocalDateTime.now();

    public static UserTestBuilder aUser() {
        return new UserTestBuilder();
    }

    public UserTestBuilder withUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public UserTestBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public UserTestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserTestBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserTestBuilder withCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public User build() {
        User user = new User(username, password, email);
        user.setUserId(userId);
        user.setCreatedAt(createdAt);
        return user;
    }
}