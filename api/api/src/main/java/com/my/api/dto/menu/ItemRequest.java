package com.my.api.dto.menu;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.UpperCamelCaseStrategy.class)
public class ItemRequest {

    String menuId;

    String menuName;

    String description;

    String imageData;

    double price;

    int discount;

    String ownShop;

}
