package com.my.api.controller;

import com.my.api.dto.create.CreateUserRequest;
import com.my.api.dto.create.CreateUserResponse;
import com.my.api.dto.login.LoginRequest;
import com.my.api.dto.login.UserLoginResponse;
import com.my.api.service.UserLoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/user/")
public class UserLoginController {

    @Autowired
    UserLoginService userLoginService;

    @PostMapping("login")
    UserLoginResponse login(@Valid @RequestBody LoginRequest request){
        return userLoginService.login(request);
    }

    @GetMapping("get/{username}")
    UserLoginResponse userDetailByUsername(@PathVariable String username) {
        return userLoginService.getUserDetailByUsername(username);
    }

    @PostMapping("register")
    CreateUserResponse createUser(@Valid @RequestBody CreateUserRequest request) {
        return userLoginService.register(request);
    }

}
