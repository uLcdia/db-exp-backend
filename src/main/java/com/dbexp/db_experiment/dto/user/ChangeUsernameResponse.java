package com.dbexp.db_experiment.dto.user;

import java.time.LocalDateTime;

public class ChangeUsernameResponse {

    private Long userId;
    private String oldUsername;
    private String newUsername;
    private LocalDateTime updatedAt;

    // Constructors
    public ChangeUsernameResponse(Long userId, String oldUsername, String newUsername, LocalDateTime updatedAt) {
        this.userId = userId;
        this.oldUsername = oldUsername;
        this.newUsername = newUsername;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOldUsername() {
        return oldUsername;
    }

    public void setOldUsername(String oldUsername) {
        this.oldUsername = oldUsername;
    }

    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}