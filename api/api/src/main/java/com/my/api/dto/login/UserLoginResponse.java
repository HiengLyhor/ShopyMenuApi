package com.my.api.dto.login;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.my.api.dto.StatusResponse;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class UserLoginResponse extends StatusResponse {

    String username;

    Timestamp createDate;

    Timestamp expDate;

    char isSpecial;

    char isLock;

    String token;

    Timestamp tokenExp;

}
