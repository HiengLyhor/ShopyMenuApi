package com.my.api.service;

import com.my.api.dto.create.CreateUserRequest;
import com.my.api.dto.create.CreateUserResponse;
import com.my.api.dto.login.LoginRequest;
import com.my.api.dto.login.UserLoginResponse;
import com.my.api.dto.user.AllUserRequest;
import com.my.api.dto.user.AllUserResponse;
import com.my.api.model.UserLogin;
import jakarta.validation.Valid;

import java.util.List;

public interface UserLoginService {

    UserLoginResponse getUserDetailByUsername(String username);

    UserLoginResponse login(LoginRequest request);

    CreateUserResponse register(@Valid CreateUserRequest request);

    AllUserResponse getAllUsers(AllUserRequest request);
}
