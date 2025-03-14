package com.my.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "User_Login")
public class UserLogin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    Long userId;

    @Column(name = "Username")
    String username;

    @Column(name = "OwnShop")
    String ownShop;

    @Column(name = "Password")
    String password;

    @Column(name = "CreateDate")
    Timestamp createDate;

    @Column(name = "ExpDate")
    Timestamp expDate;

    @Column(name = "IsLock")
    char isLock;

    @Column(name = "IsSpecial")
    char isSpecial;

    @PrePersist
    public void beforeSave() {
        this.isLock = 'N';
        this.createDate = new Timestamp(System.currentTimeMillis());
        this.expDate = new Timestamp(System.currentTimeMillis() + 604800000); // 7 Days
    }

}
