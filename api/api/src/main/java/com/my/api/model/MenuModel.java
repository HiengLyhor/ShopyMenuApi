package com.my.api.model;

import com.my.api.model.primarykey.RestaurantFoodPrimaryKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@ToString
@IdClass(RestaurantFoodPrimaryKey.class)
@Table(name = "Restaurant_Food")
public class MenuModel {

    @Id
    @Column(name = "own_shop")
    String ownShop;

    @Id
    @Column(name = "food_id")
    String foodId;

    @Column(name = "name")
    String name;

    @Column(name = "price")
    double price;

    @Column(name = "discount")
    int discount;

    @Column(name = "is_active")
    String isActive; // Y or N

    @Column(name = "create_date")
    Timestamp createDate;

    @Column(name = "food_detail")
    String foodDetail;

    @Column(name = "food_img")
    byte[] foodImg;

    @Column(name = "Venue_Name")
    String venueName;

    public MenuModel(String ownShop) {
        this.isActive = "Y";
        this.createDate = new Timestamp(System.currentTimeMillis());
        this.ownShop = ownShop;
        this.foodId = "Food-" + ownShop.substring(0, 3) + "-" + System.currentTimeMillis();
    }

    public MenuModel() {}

}
