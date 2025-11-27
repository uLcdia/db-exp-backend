package com.dbexp.db_experiment.dto.user;

import java.time.LocalDateTime;

public class DeleteAccountResponse {

    private Long userId;
    private LocalDateTime deletedAt;
    private String message;

    // Constructors
    public DeleteAccountResponse(Long userId, LocalDateTime deletedAt, String message) {
        this.userId = userId;
        this.deletedAt = deletedAt;
        this.message = message;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}