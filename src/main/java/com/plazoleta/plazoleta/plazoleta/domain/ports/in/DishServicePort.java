package com.plazoleta.plazoleta.plazoleta.domain.ports.in;

import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;

public interface DishServicePort {
    void createDish(DishModel dishModel);
}
