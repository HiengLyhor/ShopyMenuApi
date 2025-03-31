package com.my.api.service;

import com.my.api.dto.StatusResponse;
import com.my.api.dto.menu.ItemRequest;
import com.my.api.model.MenuModel;

import java.util.Optional;

public interface ItemService {

    StatusResponse createNewMenu(ItemRequest request);

    Optional<MenuModel> getFoodDetail(String foodId);

}
