package com.my.api.repository;

import com.my.api.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

    UserLogin findByUsername(String username);

    UserLogin findByOwnShop(String ownShop);

}
