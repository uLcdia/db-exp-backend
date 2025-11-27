package com.dbexp.db_experiment.dto.user;

import jakarta.validation.constraints.NotBlank;

public class GetUserByIdRequest {
    @NotBlank(message = "User ID cannot be blank")
    private Long userId;

    // Constructors
    public GetUserByIdRequest(Long userId) {
        this.userId = userId;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
