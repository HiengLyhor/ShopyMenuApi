package com.my.api.service.implement;

import com.my.api.dto.create.CreateUserRequest;
import com.my.api.dto.restaurant.RestaurantDetailResponse;
import com.my.api.enums.ActionType;
import com.my.api.enums.TableName;
import com.my.api.model.AuditTraceModel;
import com.my.api.model.RestaurantModel;
import com.my.api.model.UserLogin;
import com.my.api.repository.RestaurantRepository;
import com.my.api.repository.UserLoginRepository;
import com.my.api.service.AuditService;
import com.my.api.service.RestaurantService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserLoginRepository userLoginRepository;

    @Autowired
    AuditService auditService;

    @Override
    public void createRestaurant(CreateUserRequest request, String shopId, String creator, String joinVenue, String shopName) {

        try {

            RestaurantModel restaurantModel = new RestaurantModel();
            restaurantModel.setOwnShop(shopId);
            restaurantModel.setShopName(shopName);
            restaurantModel.setContactInfo(request.getContactInfo());
            restaurantModel.setTelegramId(request.getTelegramId());
            restaurantModel.setCreateBy(creator);

            if (joinVenue != null) {
                RestaurantModel venue = restaurantRepository.findByVenueName(joinVenue);
                if (venue != null) {
                    restaurantModel.setVenueName(venue.getVenueName());
                    restaurantModel.setOwnShop(venue.getShopName());
                }
                else restaurantModel.setVenueName(generateVenue());
            } else {
                restaurantModel.setVenueName(generateVenue());
            }

            auditService.saveAudit(new AuditTraceModel(TableName.RESTAURANT_CLIENT, ActionType.CREATE, creator, null, restaurantModel.toString()));
            restaurantRepository.save(restaurantModel);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private String generateVenue() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);

        return "Ven" + formattedDateTime;
    }

    @Override
    public RestaurantDetailResponse getRestaurantInfo(String resId) {

        try {

            RestaurantDetailResponse restaurantDetailResponse = new RestaurantDetailResponse();

            Optional<RestaurantModel> restaurantData = restaurantRepository.findById(resId);

            if (restaurantData.isEmpty()) {
                return restaurantDetailResponse.noRecordFound();
            }

            UserLogin loginDetail = userLoginRepository.findByOwnShop(resId);

            BeanUtils.copyProperties(restaurantData.get(), restaurantDetailResponse);
            restaurantDetailResponse.setUsername(loginDetail.getUsername());
            restaurantDetailResponse.setExpireDate(loginDetail.getExpDate());
            restaurantDetailResponse.setCode(HttpStatus.OK.value());
            restaurantDetailResponse.setMessage("Data retrieved successfully.");

            return restaurantDetailResponse;

        } catch (Exception e) {
            return new RestaurantDetailResponse().errorResponse(e.getMessage());
        }

    }

}
