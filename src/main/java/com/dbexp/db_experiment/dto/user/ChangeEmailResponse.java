package com.dbexp.db_experiment.dto.user;

import java.time.LocalDateTime;

public class ChangeEmailResponse {

    private Long userId;
    private String oldEmail;
    private String newEmail;
    private LocalDateTime updatedAt;
    private String message;

    // Constructors
    public ChangeEmailResponse(Long userId, String oldEmail, String newEmail, LocalDateTime updatedAt, String message) {
        this.userId = userId;
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
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

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
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