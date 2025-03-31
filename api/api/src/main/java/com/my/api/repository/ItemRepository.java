package com.my.api.repository;

import com.my.api.model.MenuModel;
import com.my.api.model.primarykey.RestaurantFoodPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<MenuModel, RestaurantFoodPrimaryKey> {

    Optional<MenuModel> findByFoodId(String foodId);

    MenuModel findByNameAndOwnShop(String name, String ownShop);

}
