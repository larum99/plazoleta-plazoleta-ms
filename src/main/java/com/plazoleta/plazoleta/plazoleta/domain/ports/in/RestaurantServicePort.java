package com.plazoleta.plazoleta.plazoleta.domain.ports.in;

import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;

public interface RestaurantServicePort {
    void createRestaurant(RestaurantModel restaurantModel, String role);
}

