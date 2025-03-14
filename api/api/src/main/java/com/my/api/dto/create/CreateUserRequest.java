package com.my.api.dto.create;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
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

    @NotNull(message = "Shop own cannot be null")
    @NotBlank(message = "Shop own cannot be blank")
    String ownShop;

    @NotNull(message = "Special Key cannot be null")
    @NotBlank(message = "Special Key cannot be blank")
    String specialKey;

}
