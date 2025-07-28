package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;

import java.util.Optional;

public interface RestaurantPersistencePort {
    void saveRestaurant(RestaurantModel restaurantModel);
    RestaurantModel getRestaurantByNit(String nit);
    RestaurantModel getRestaurantByName(String name);
    Optional<RestaurantModel> getRestaurantById(Long id);

}
