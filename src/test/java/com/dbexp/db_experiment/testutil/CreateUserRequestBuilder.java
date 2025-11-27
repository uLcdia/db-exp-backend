package com.dbexp.db_experiment.testutil;

import com.dbexp.db_experiment.dto.user.CreateUserRequest;

public class CreateUserRequestBuilder {

    private String username = "testuser";
    private String email = "test@example.com";
    private String password = "password123";

    public static CreateUserRequestBuilder validRequest() {
        return new CreateUserRequestBuilder();
    }

    public CreateUserRequestBuilder withUsername(String username) {
        this.username = username;
        return this;
    }

    public CreateUserRequestBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CreateUserRequestBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public CreateUserRequest build() {
        return new CreateUserRequest(username, email, password);
    }
}