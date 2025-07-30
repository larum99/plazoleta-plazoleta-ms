package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.helpers.DishHelper;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.DishServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.CategoryPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;

import java.math.BigDecimal;

public class DishUseCase implements DishServicePort {

    private final DishPersistencePort dishPersistencePort;
    private final DishHelper helper;

    public DishUseCase(
            DishPersistencePort dishPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            CategoryPersistencePort categoryPersistencePort
    ) {
        this.dishPersistencePort = dishPersistencePort;
        this.helper = new DishHelper(dishPersistencePort, restaurantPersistencePort, categoryPersistencePort);
    }

    @Override
    public void createDish(DishModel dishModel, String role, Long ownerId) {
        helper.validateRole(role);
        helper.normalizeFields(dishModel);
        helper.validateMandatoryFields(dishModel);
        helper.validatePrice(dishModel.getPrice());

        Long restaurantId = dishModel.getRestaurant().getId();
        Long categoryId = dishModel.getCategory().getId();

        helper.validateRestaurantExists(restaurantId);
        helper.validateCategoryExists(categoryId);
        helper.validateOwnership(restaurantId, ownerId);

        dishModel.setActive(true);
        dishPersistencePort.saveDish(dishModel);
    }

    @Override
    public void updateDish(Long dishId, String description, BigDecimal price, Long ownerId) {
        DishModel dish = helper.getValidatedDishForUpdate(dishId, description, price, ownerId);

        dish.setDescription(description.trim());
        dish.setPrice(price);

        dishPersistencePort.updateDish(dish);
    }
}