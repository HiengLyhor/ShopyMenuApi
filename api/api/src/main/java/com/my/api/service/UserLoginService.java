package com.my.api.service;

import com.my.api.dto.create.CreateUserRequest;
import com.my.api.dto.create.CreateUserResponse;
import com.my.api.dto.login.LoginRequest;
import com.my.api.dto.login.UserLoginResponse;
import jakarta.validation.Valid;

public interface UserLoginService {

    UserLoginResponse getUserDetailByUsername(String username);

    UserLoginResponse login(LoginRequest request);

    CreateUserResponse register(@Valid CreateUserRequest request);
}
