package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;

public interface RestaurantPersistencePort {
    void saveRestaurant(RestaurantModel restaurantModel);
    RestaurantModel getRestaurantByNit(String nit);
    RestaurantModel getRestaurantByName(String name);
}
