package com.my.api.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.my.api.dto.StatusResponse;
import com.my.api.model.UserLogin;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter @Setter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class AllUserResponse extends StatusResponse {
    List<UserLogin> userLogins;

    public AllUserResponse(int code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    public AllUserResponse(List<UserLogin> allUser) {
        this.userLogins = allUser;
        this.setCode(HttpStatus.OK.value());
        this.setMessage("All users retrieved.");
    };

}
