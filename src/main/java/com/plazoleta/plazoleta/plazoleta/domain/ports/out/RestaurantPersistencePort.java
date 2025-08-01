package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;

import java.util.Optional;

public interface RestaurantPersistencePort {
    void saveRestaurant(RestaurantModel restaurantModel);
    RestaurantModel getRestaurantByNit(String nit);
    RestaurantModel getRestaurantByName(String name);
    Optional<RestaurantModel> getRestaurantById(Long id);
    PageResult<RestaurantModel> listRestaurantsOrderedByName(int page, int size);
}
