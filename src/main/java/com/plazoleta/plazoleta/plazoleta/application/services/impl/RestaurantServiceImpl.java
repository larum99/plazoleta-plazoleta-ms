package com.plazoleta.plazoleta.plazoleta.application.services.impl;

import com.plazoleta.plazoleta.commons.configurations.utils.Constants;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveRestaurantRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveRestaurantResponse;
import com.plazoleta.plazoleta.plazoleta.application.mappers.RestaurantDtoMapper;
import com.plazoleta.plazoleta.plazoleta.application.services.RestaurantService;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.RestaurantServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.RoleValidatorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantServicePort restaurantServicePort;
    private final RestaurantDtoMapper restaurantDtoMapper;
    private final RoleValidatorPort roleValidatorPort;

    @Override
    public SaveRestaurantResponse saveRestaurant(SaveRestaurantRequest request, String token) {
        String role = roleValidatorPort.extractRole(token);
        RestaurantModel model = restaurantDtoMapper.requestToModel(request);
        restaurantServicePort.createRestaurant(model, role);
        return new SaveRestaurantResponse(Constants.SAVE_RESTAURANT_RESPONSE_MESSAGE, LocalDateTime.now());
    }

}
