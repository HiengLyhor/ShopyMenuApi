package com.my.api.controller;

import com.my.api.dto.restaurant.RestaurantDetailResponse;
import com.my.api.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/restaurant/")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @GetMapping("{resId}")
    RestaurantDetailResponse restaurantDetail(@Valid @PathVariable String resId) {
        return restaurantService.getRestaurantInfo(resId);
    }

}
