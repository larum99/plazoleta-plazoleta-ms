package com.plazoleta.plazoleta.plazoleta.application.services;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveRestaurantRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveRestaurantResponse;

public interface RestaurantService {
    SaveRestaurantResponse save(SaveRestaurantRequest request);
}
