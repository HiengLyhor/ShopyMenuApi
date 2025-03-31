package com.my.api.model.primarykey;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RestaurantFoodPrimaryKey implements Serializable {

    @Column(name = "own_shop")
    String ownShop;

    @Column(name = "food_id")
    String foodId;

}
