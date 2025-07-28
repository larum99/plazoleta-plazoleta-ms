package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;

import java.util.Optional;

public interface DishPersistencePort {
    void saveDish(DishModel dishModel);
    Optional<DishModel> getDishById(Long id);
}
