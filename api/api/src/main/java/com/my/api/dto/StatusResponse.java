package com.my.api.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter @Setter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class StatusResponse {

    int code;

    String message;

    public StatusResponse() {}

    public StatusResponse successResponse() {
        StatusResponse response = new StatusResponse();

        response.setCode(HttpStatus.OK.value());
        response.setMessage("Success.");

        return response;
    }

    public StatusResponse errorResponse(String message) {
        StatusResponse response = new StatusResponse();

        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(message);

        return response;
    }


}
