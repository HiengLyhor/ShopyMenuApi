package com.my.api.service.implement;

import com.my.api.config.Encryption;
import com.my.api.config.JwtService;
import com.my.api.dto.create.CreateUserRequest;
import com.my.api.dto.create.CreateUserResponse;
import com.my.api.dto.login.LoginRequest;
import com.my.api.dto.login.UserLoginResponse;
import com.my.api.dto.restaurant.RestaurantDetailResponse;
import com.my.api.dto.user.AllUserRequest;
import com.my.api.dto.user.AllUserResponse;
import com.my.api.dto.user.UserDetailsDto;
import com.my.api.enums.ActionType;
import com.my.api.enums.MyEnum;
import com.my.api.enums.TableName;
import com.my.api.model.AuditTraceModel;
import com.my.api.model.UserLogin;
import com.my.api.repository.UserLoginRepository;
import com.my.api.service.AuditService;
import com.my.api.service.RestaurantService;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.my.api.config.Encryption.decrypt;

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

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    AuditService auditService;

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

            request.setPassword(decrypt(request.getPassword()));

            UserLoginResponse response = new UserLoginResponse();

            Authentication authentication = authenticationManager.authenticate(new
                    UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            BeanUtils.copyProperties(request, response);

            if (authentication.isAuthenticated()) {

                auditService.saveAudit(new AuditTraceModel(TableName.USER_LOGIN, ActionType.LOGIN, request.getUsername(), null, request.toString()));

                String token = jwtService.generateToken(request.getUsername());

                UserLogin userDetails = userLoginRepository.findByUsername(request.getUsername());
                RestaurantDetailResponse restaurantInfo = restaurantService.getRestaurantInfo(userDetails.getOwnShop());

                BeanUtils.copyProperties(userDetails, response);
                response.setCode(HttpStatus.OK.value());
                response.setMessage("Login successfully.");
                response.setToken(Encryption.encrypt(token));
                response.setVenueName(restaurantInfo.getVenueName());
                response.setShopName(restaurantInfo.getShopName());
                response.setTokenExp(new Timestamp(System.currentTimeMillis() + 600000));

                return response;
            }

            response.setCode(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Login failed.");

            return response;

        }
        catch (Exception ex) {
            return new UserLoginResponse().responseError(ex.getMessage());
        }

    }

    @Override
    public CreateUserResponse register(CreateUserRequest request) {

        try {

            CreateUserResponse response = new CreateUserResponse();

            BeanUtils.copyProperties(request, response);

            if (!decrypt(request.getSpecialKey()).equals(privateString)) {
                return buildErrorResponse(response, HttpStatus.UNAUTHORIZED, "Special key does not match.");
            }

            UserLogin requesterData = userLoginRepository.findByUsername(request.getRequester());
            if (requesterData == null) {
                return buildErrorResponse(response, HttpStatus.UNAUTHORIZED, "Requester does not exist.");
            }

            UserLogin existingUser = userLoginRepository.findByUsername(request.getUsername());
            if (existingUser  != null) {
                return buildErrorResponse(response, HttpStatus.CONFLICT, "This username already exists.");
            }

            if (request.getRole().equalsIgnoreCase(MyEnum.USER.toString()) && "N".equalsIgnoreCase(request.getOwnShop())) {
                return buildErrorResponse(response, HttpStatus.CONFLICT, "Creating user with OwnShop is N is not allowed.");
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String formattedDateTime = now.format(formatter);

            String shopId = "Res" + formattedDateTime;

            // Create and save new user
            UserLogin newUser = new UserLogin();
            BeanUtils.copyProperties(request, newUser);
            String decryptedPassword = decrypt(request.getPassword());
            newUser.setPassword(encoder.encode(decryptedPassword));
            newUser.setOwnShop(shopId);
            newUser.beforeSave();

            if (request.getRole().equalsIgnoreCase(MyEnum.USER.toString())) {
                newUser.setIsSpecial('N');
                restaurantService.createRestaurant(request, shopId, requesterData.getUsername(), request.getVenueName(), request.getShopName());
            } else {
                newUser.setIsSpecial('Y');
                newUser.setOwnShop("N");
            }

            auditService.saveAudit(new AuditTraceModel(TableName.USER_LOGIN, ActionType.CREATE, requesterData.getUsername(), null, request.toString()));
            userLoginRepository.save(newUser);

            response.setExpDate(new Timestamp(System.currentTimeMillis() + 604800000));
            response.setCode(HttpStatus.OK.value());
            response.setMessage("User created successfully.");

            return response;

        }
        catch (Exception ex) {
            return new CreateUserResponse().errorResponse(ex.getMessage());
        }

    }

    @Override
    public AllUserResponse getAllUsers(AllUserRequest request) {
        try {

            UserLogin userRequest = userLoginRepository.findByUsername(request.getUserRequest());

            if (userRequest == null) {
                return new AllUserResponse(HttpStatus.UNAUTHORIZED.value(), "You don't have rights to access this.");
            }

            if (userRequest.getIsSpecial() != 'Y') {
                return new AllUserResponse(HttpStatus.UNAUTHORIZED.value(), "You don't have permission to access.");
            }

            List<UserLogin> allUser = userLoginRepository.findAll();
            List<UserDetailsDto> userDetailsDtos = new ArrayList<>();

            for (UserLogin singleUser : allUser) {
                UserDetailsDto singleUserDetail = new UserDetailsDto();
                BeanUtils.copyProperties(singleUser, singleUserDetail);
                userDetailsDtos.add(singleUserDetail);
            }

            return new AllUserResponse(userDetailsDtos);

        }
        catch (Exception ex) {
            return new AllUserResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred during the request.");
        }
    }

    private CreateUserResponse buildErrorResponse(CreateUserResponse response, HttpStatus status, String message) {
        response.setCode(status.value());
        response.setMessage(message);
        return response;
    }
}
