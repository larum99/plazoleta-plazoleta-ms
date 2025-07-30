package com.plazoleta.plazoleta.plazoleta.domain.ports.in;

import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;

import java.math.BigDecimal;

public interface DishServicePort {
    void createDish(DishModel dishModel, String role, Long ownerId);
    void updateDish(Long dishId, String description, BigDecimal price, Long ownerId);


}
