package com.plazoleta.plazoleta.plazoleta.application.services;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateEmployeeRestaurantRequest;

public interface EmployeeRestaurantService {
    void assignEmployeeToRestaurant(CreateEmployeeRestaurantRequest request, String token);
}

