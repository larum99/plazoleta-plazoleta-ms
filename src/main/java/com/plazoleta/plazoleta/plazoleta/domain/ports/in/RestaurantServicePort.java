package com.plazoleta.plazoleta.plazoleta.domain.ports.in;

import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;

public interface RestaurantServicePort {
    void createRestaurant(RestaurantModel restaurantModel, String role);
    PageResult<RestaurantModel> listRestaurants(int page, int size);
}

