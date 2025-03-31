package com.my.api.controller;

import com.my.api.dto.StatusResponse;
import com.my.api.dto.menu.ItemRequest;
import com.my.api.model.MenuModel;
import com.my.api.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("v1/menu/")
public class MenuController {

    @Autowired
    ItemService itemService;

    @PostMapping("new")
    public StatusResponse createNewMenu(@RequestBody ItemRequest request) {
        return itemService.createNewMenu(request);
    }

    @GetMapping("{foodId}")
    public Optional<MenuModel> getMenuDetail(@PathVariable String foodId) {
        return itemService.getFoodDetail(foodId);
    }

}
