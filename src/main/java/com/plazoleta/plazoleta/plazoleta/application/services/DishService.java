package com.plazoleta.plazoleta.plazoleta.application.services;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveDishResponse;

public interface DishService {
    SaveDishResponse save(SaveDishRequest request);
}
