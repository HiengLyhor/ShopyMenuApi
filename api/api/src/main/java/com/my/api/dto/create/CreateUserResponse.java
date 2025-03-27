package com.my.api.dto.create;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.my.api.dto.StatusResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class CreateUserResponse extends StatusResponse {

    String username;

    String ownShop;

    String role;

    Timestamp expDate;

    public CreateUserResponse() {}

    public CreateUserResponse errorResponse(String message) {
        CreateUserResponse response = new CreateUserResponse();
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(message);
        return response;
    }

}
