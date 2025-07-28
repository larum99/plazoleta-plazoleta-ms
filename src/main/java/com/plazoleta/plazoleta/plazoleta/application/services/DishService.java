package com.plazoleta.plazoleta.plazoleta.application.services;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.UpdateDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveDishResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.UpdateDishResponse;

public interface DishService {
    SaveDishResponse save(SaveDishRequest request);
    UpdateDishResponse updateDish(Long dishId, UpdateDishRequest request);
}
