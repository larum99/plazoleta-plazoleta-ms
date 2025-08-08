package com.plazoleta.plazoleta.plazoleta.application.services;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateEmployeeRestaurantRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.CreateEmployeeRestaurantResponse;

public interface EmployeeRestaurantService {
    CreateEmployeeRestaurantResponse assignEmployeeToRestaurant(CreateEmployeeRestaurantRequest request, String token);
}

