package com.plazoleta.plazoleta.plazoleta.application.services.impl;

import com.plazoleta.plazoleta.commons.configurations.utils.Constants;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.UpdateDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveDishResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.UpdateDishResponse;
import com.plazoleta.plazoleta.plazoleta.application.mappers.DishDtoMapper;
import com.plazoleta.plazoleta.plazoleta.application.services.DishService;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.DishServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.RoleValidatorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishServicePort dishServicePort;
    private final DishDtoMapper dishDtoMapper;
    private final RoleValidatorPort roleValidatorPort;

    @Override
    public SaveDishResponse saveDish(SaveDishRequest request, String token) {
        Long ownerId = roleValidatorPort.extractUserId(token);
        String role = roleValidatorPort.extractRole(token);
        DishModel model = dishDtoMapper.requestToModel(request);
        dishServicePort.createDish(model, role, ownerId);
        return new SaveDishResponse(Constants.SAVE_DISH_RESPONSE_MESSAGE, LocalDateTime.now());
    }

    @Override
    public UpdateDishResponse updateDish(Long dishId, UpdateDishRequest request, String token) {
        Long ownerId = roleValidatorPort.extractUserId(token);
        dishServicePort.updateDish(dishId, request.description(), request.price(), ownerId);
        return new UpdateDishResponse(Constants.UPDATE_DISH_SUCCESS_MESSAGE, LocalDateTime.now());
    }
}
