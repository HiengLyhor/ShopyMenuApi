package com.my.api.service.implement;

import com.my.api.dto.StatusResponse;
import com.my.api.dto.menu.ItemRequest;
import com.my.api.enums.ActionType;
import com.my.api.enums.TableName;
import com.my.api.model.AuditTraceModel;
import com.my.api.model.MenuModel;
import com.my.api.model.RestaurantModel;
import com.my.api.repository.ItemRepository;
import com.my.api.repository.RestaurantRepository;
import com.my.api.service.AuditService;
import com.my.api.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    AuditService auditService;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public StatusResponse createNewMenu(ItemRequest request) {
        try {

            // Guard clause
            boolean validate = guardCreate(request);

            if (!validate) {
                return new StatusResponse().errorResponse("Request data is invalid.");
            }

            // Validate name & ownShop
            MenuModel byNameAndOwnShop = itemRepository.findByNameAndOwnShop(request.getMenuName(), request.getOwnShop());

            if (byNameAndOwnShop != null) {
                return new StatusResponse().errorResponse("Your menu name is already existed.");
            }

            // Get venue name data
            Optional<RestaurantModel> restaurantModel = restaurantRepository.findById(request.getOwnShop());

            String venueName = "";
            if (restaurantModel.isPresent()) {
                venueName = restaurantModel.get().getVenueName();
            }

            byte[] imageByte = Base64.getDecoder().decode(request.getImageData());

            MenuModel menuModel = new MenuModel(request.getOwnShop());

            menuModel.setDiscount(request.getDiscount());
            menuModel.setName(request.getMenuName());
            menuModel.setPrice(request.getPrice());
            menuModel.setFoodDetail(request.getDescription());
            menuModel.setFoodImg(imageByte);
            menuModel.setVenueName(venueName);

            request.setImageData("Check in DB");
            auditService.saveAudit(new AuditTraceModel(TableName.RESTAURANT_FOOD, ActionType.CREATE, request.getRequester(), null, request.toString()));
            itemRepository.save(menuModel);

            return new StatusResponse().successResponse();

        }
        catch (IllegalArgumentException e) {
            return new StatusResponse().errorResponse("Invalid Image format.");
        }
        catch (Exception e) {
            return new StatusResponse().errorResponse(e.getMessage());
        }
    }

    @Override
    public Optional<MenuModel>  getFoodDetail(String foodId) {

        try {

            return itemRepository.findByFoodId(foodId);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private boolean guardCreate(ItemRequest request) {

        if (request == null) return false;

        return (request.getOwnShop() != null) && (request.getImageData() != null && !request.getImageData().isEmpty()) && (request.getMenuName() != null) && (request.getPrice() > 0);

    }

    private boolean guardEdit(ItemRequest request) {

        if (request == null) return false;

        return (request.getMenuId() != null) && (request.getOwnShop() != null) && (request.getImageData() != null && !request.getImageData().isEmpty()) && (request.getMenuName() != null) && (request.getPrice() > 0);

    }

}
