package com.my.api.dto.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class UserDetailsDto {

    String username;
    String ownShop;
    Timestamp createDate;
    Timestamp expDate;
    char isLock;
    char isSpecial;

}
