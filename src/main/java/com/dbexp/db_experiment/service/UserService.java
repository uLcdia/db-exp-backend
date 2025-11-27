package com.dbexp.db_experiment.service;

import com.dbexp.db_experiment.dto.user.ChangeEmailRequest;
import com.dbexp.db_experiment.dto.user.ChangeEmailResponse;
import com.dbexp.db_experiment.dto.user.ChangePasswordRequest;
import com.dbexp.db_experiment.dto.user.ChangePasswordResponse;
import com.dbexp.db_experiment.dto.user.ChangeUsernameRequest;
import com.dbexp.db_experiment.dto.user.ChangeUsernameResponse;
import com.dbexp.db_experiment.dto.user.CreateUserRequest;
import com.dbexp.db_experiment.dto.user.CreateUserResponse;
import com.dbexp.db_experiment.dto.user.DeleteAccountRequest;
import com.dbexp.db_experiment.dto.user.DeleteAccountResponse;
import com.dbexp.db_experiment.dto.user.GetUserByIdRequest;
import com.dbexp.db_experiment.dto.user.GetUserByIdResponse;

public interface UserService {
    GetUserByIdResponse getUserById(GetUserByIdRequest request);

    CreateUserResponse createUser(CreateUserRequest request);

    ChangeUsernameResponse changeUsername(Long userId, ChangeUsernameRequest request);

    ChangePasswordResponse changePassword(Long userId, ChangePasswordRequest request);

    ChangeEmailResponse changeEmail(Long userId, ChangeEmailRequest request);

    DeleteAccountResponse deleteAccount(Long userId, DeleteAccountRequest request);
}