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
        return new RestaurantDetailResponse("404");
    }

    RestaurantDetailResponse(String notFound) {
        this.setCode(HttpStatus.NOT_FOUND.value());
        this.setMessage("Restaurant not found.");
    }

    public RestaurantDetailResponse() {
    }

}
