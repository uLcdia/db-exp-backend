package com.dbexp.db_experiment.dto.user;

import java.time.LocalDateTime;

public class ChangePasswordResponse {

    private Long userId;
    private LocalDateTime updatedAt;
    private String message;

    // Constructors
    public ChangePasswordResponse(Long userId, LocalDateTime updatedAt, String message) {
        this.userId = userId;
        this.updatedAt = updatedAt;
        this.message = message;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}