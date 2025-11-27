package com.dbexp.db_experiment.dto.user;

import java.time.LocalDateTime;

public class GetUserByIdResponse {

    private Long userId;
    private String username;
    private String email;
    private LocalDateTime creationTime;

    // Constructors
    public GetUserByIdResponse(Long userId, String username, String email, LocalDateTime creationTime) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.creationTime = creationTime;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }
}