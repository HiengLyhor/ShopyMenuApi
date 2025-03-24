package com.my.api.service;

import com.my.api.dto.create.CreateUserRequest;
import com.my.api.dto.restaurant.RestaurantDetailResponse;

public interface RestaurantService {

    void createRestaurant(CreateUserRequest request, String shopId, String creator);

    RestaurantDetailResponse getRestaurantInfo(String resId);

}
