package com.my.api.dto.restaurant;

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
public class RestaurantDetailResponse extends StatusResponse {

    String shopName;

    String venueName;

    String contactInfo;

    String telegramId;

    String username;

    String createBy;

    Timestamp lastModified;

    Timestamp expireDate;

    public RestaurantDetailResponse noRecordFound() {
        RestaurantDetailResponse response = new RestaurantDetailResponse();
        response.setCode(HttpStatus.NOT_FOUND.value());
        response.setMessage("Record does not found in our system.");
        return response;
    }

    public RestaurantDetailResponse errorResponse(String message) {
        RestaurantDetailResponse response = new RestaurantDetailResponse();
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage(message);
        return response;
    }

    public RestaurantDetailResponse() {}

}
