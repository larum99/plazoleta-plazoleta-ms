package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.DishCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;

import java.util.Optional;

public interface DishPersistencePort {
    void saveDish(DishModel dishModel);
    Optional<DishModel> getDishById(Long id);
    void updateDish(DishModel dishModel);
    PageResult<DishModel> getMenuByCriteria(DishCriteria criteria);
}
