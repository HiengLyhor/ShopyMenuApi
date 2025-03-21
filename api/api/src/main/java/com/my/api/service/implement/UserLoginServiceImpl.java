package com.my.api.service.implement;

import com.my.api.config.Encryption;
import com.my.api.config.JwtService;
import com.my.api.dto.create.CreateUserRequest;
import com.my.api.dto.create.CreateUserResponse;
import com.my.api.dto.login.LoginRequest;
import com.my.api.dto.login.UserLoginResponse;
import com.my.api.enums.MyEnum;
import com.my.api.model.UserLogin;
import com.my.api.repository.UserLoginRepository;
import com.my.api.service.UserLoginService;
import org.apache.logging.log4j.util.InternalException;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Objects;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Value("${credential-for-create-user}")
    private String privateString;

    @Override
    public UserLoginResponse getUserDetailByUsername(String username) {

        try {

            UserLoginResponse response = new UserLoginResponse();

            if (Strings.isBlank(username)) {
                throw new InternalException("Username cannot be empty.");
            }

            UserLogin userByUsername = userLoginRepository.findByUsername(username);

            if (Objects.isNull(userByUsername)) {
                throw new UsernameNotFoundException("User cannot be found.");
            }

            BeanUtils.copyProperties(userByUsername, response);

            response.setCode(HttpStatus.OK.value());
            response.setMessage("Data retrieved successfully.");

            return response;

        }
        catch (Exception ex) {
            throw new InternalException("An error occurred during the process.");
        }

    }

    @Override
    public UserLoginResponse login(LoginRequest request) {

        try{

            request.setPassword(Encryption.decrypt(request.getPassword()));

            UserLoginResponse response = new UserLoginResponse();

            Authentication authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            BeanUtils.copyProperties(request, response);

            if (authentication.isAuthenticated()) {

                String token = jwtService.generateToken(request.getUsername());

                UserLogin userDetails = userLoginRepository.findByUsername(request.getUsername());

                BeanUtils.copyProperties(userDetails, response);
                response.setCode(HttpStatus.OK.value());
                response.setMessage("Login successfully.");
                response.setToken(Encryption.encrypt(token));
                response.setTokenExp(new Timestamp(System.currentTimeMillis() + 600000));

                return response;
            }

            response.setCode(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Login failed.");

            return response;

        }
        catch (Exception ex) {
            throw new InternalException("An error occurred during the process.");
        }

    }

    @Override
    public CreateUserResponse register(CreateUserRequest request) {

        try {

            CreateUserResponse response = new CreateUserResponse();

            BeanUtils.copyProperties(request, response);

            UserLogin userLoginRepositoryByUsername = userLoginRepository.findByUsername(request.getUsername());

            if (!Objects.isNull(userLoginRepositoryByUsername)) {
                response.setCode(HttpStatus.CONFLICT.value());
                response.setMessage("This username is already existed.");

                return response;
            }

            if (!request.getSpecialKey().equals(privateString)) {
                response.setCode(HttpStatus.UNAUTHORIZED.value());
                response.setMessage("Special key does not match.");

                return response;
            }

            UserLogin userData = new UserLogin();

            BeanUtils.copyProperties(request, userData);
            userData.setPassword(encoder.encode(request.getPassword()));
            userData.beforeSave();

            if (request.getRole().equalsIgnoreCase(MyEnum.USER.toString())) {
                userData.setIsSpecial('N');
            }

            userLoginRepository.save(userData);
            response.setExpDate(new Timestamp(System.currentTimeMillis() + 604800000));
            response.setCode(HttpStatus.OK.value());
            response.setMessage("User created successfully.");

            return response;

        }
        catch (Exception ex) {
            throw new InternalException("An error occurred during the process.");
        }

    }
}
