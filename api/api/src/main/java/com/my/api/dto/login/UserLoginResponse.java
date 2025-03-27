package com.my.api.dto.login;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.my.api.dto.StatusResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter @Setter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class UserLoginResponse extends StatusResponse {

    String username;

    Timestamp createDate;

    Timestamp expDate;

    String venueName;

    String shopName;

    char isSpecial;

    char isLock;

    String token;

    Timestamp tokenExp;

    public UserLoginResponse() {}

    public UserLoginResponse responseError(String message) {
        UserLoginResponse response = new UserLoginResponse();
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(message);
        return response;
    }

}
