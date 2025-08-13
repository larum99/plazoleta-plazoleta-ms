package com.plazoleta.plazoleta.plazoleta.application.services;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.ListRestaurantRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveRestaurantRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.PagedRestaurantResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveRestaurantResponse;

public interface RestaurantService {
    SaveRestaurantResponse saveRestaurant(SaveRestaurantRequest request, String token);
    PagedRestaurantResponse listRestaurants(ListRestaurantRequest request);
    boolean existsById(Long restaurantId);
}
