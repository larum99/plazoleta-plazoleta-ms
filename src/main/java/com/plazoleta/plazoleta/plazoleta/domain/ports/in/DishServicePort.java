package com.plazoleta.plazoleta.plazoleta.domain.ports.in;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.DishCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;

import java.math.BigDecimal;

public interface DishServicePort {
    void createDish(DishModel dishModel, String role, Long ownerId);
    void updateDish(Long dishId, String description, BigDecimal price, Long ownerId);
    void changeDishStatus(Long dishId, Long ownerId, Boolean newStatus);
    PageResult<DishModel> getMenuByCriteria(DishCriteria criteria);
}
