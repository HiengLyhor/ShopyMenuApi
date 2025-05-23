package com.my.api.dto.create;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class CreateUserRequest {

    @NotNull(message = "Username cannot be null")
    @NotBlank(message = "Username cannot be blank")
    String username;

    @NotNull(message = "Password cannot be null")
    @NotBlank(message = "Password cannot be blank")
    String password;

    @NotNull(message = "Role cannot be null")
    @NotBlank(message = "Role cannot be blank")
    String role;

    @NotNull(message = "OwnShop cannot be null")
    @NotBlank(message = "OwnShop cannot be blank")
    String ownShop;

    @NotNull(message = "SpecialKey cannot be null")
    @NotBlank(message = "SpecialKey cannot be blank")
    String specialKey;

    @NotNull(message = "Requester cannot be null")
    @NotBlank(message = "Requester cannot be blank")
    String requester;

    String contactInfo;

    String telegramId;

    String venueName;

    @NotNull(message = "ShopName cannot be null")
    @NotBlank(message = "ShopName cannot be blank")
    String shopName;

}
