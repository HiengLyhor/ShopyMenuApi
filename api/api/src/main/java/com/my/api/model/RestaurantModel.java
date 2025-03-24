package com.my.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "Restaurant_Client")
public class RestaurantModel {

    @Id
    @Column(name = "OwnShop")
    String ownShop;

    @Column(name = "ShopName")
    String shopName;

    @Column(name = "ContactInfo")
    String contactInfo;

    @Column(name = "TelegramId")
    String telegramId;

    @Column(name = "CreateBy")
    String createBy;

    @Column(name = "LastModified")
    Timestamp lastModified;

    public RestaurantModel() {
        this.lastModified = new Timestamp(System.currentTimeMillis());
    }

}
