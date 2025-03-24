package com.my.api.repository;

import com.my.api.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantModel, String> {

    RestaurantModel findByVenueName(String joinVenue);
}
