package com.plazoleta.plazoleta.plazoleta.application.services;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.UpdateDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveDishResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.UpdateDishResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.UpdateDishStatusResponse;

public interface DishService {
    SaveDishResponse saveDish(SaveDishRequest request, String token);
    UpdateDishResponse updateDish(Long dishId, UpdateDishRequest request, String token);
    UpdateDishStatusResponse updateStatusDish(Long dishId, Boolean newStatus, String token);

}

