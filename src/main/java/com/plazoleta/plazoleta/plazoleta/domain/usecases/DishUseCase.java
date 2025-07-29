package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.helpers.DishHelper;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.DishServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.CategoryPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;

import java.math.BigDecimal;

public class DishUseCase implements DishServicePort {

    private final DishPersistencePort dishPersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;
    private final CategoryPersistencePort categoryPersistencePort;
    private final DishHelper dishHelper;

    public DishUseCase(
            DishPersistencePort dishPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            CategoryPersistencePort categoryPersistencePort
    ) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.dishHelper = new DishHelper();
    }

    @Override
    public void createDish(DishModel dishModel) {
        dishHelper.normalizeFields(dishModel);
        dishHelper.validateMandatoryFields(dishModel);
        dishHelper.validatePrice(dishModel.getPrice());

        Long restaurantId = dishModel.getRestaurant().getId();
        Long categoryId = dishModel.getCategory().getId();

        validateRestaurantExists(restaurantId);
        validateCategoryExists(categoryId);
        validateOwnership(restaurantId, DomainConstants.MOCK_OWNER_ID);

        dishModel.setActive(true);

        dishPersistencePort.saveDish(dishModel);
    }

    @Override
    public void updateDish(Long dishId, String description, BigDecimal price) {
        if (dishId == null) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_DISH_ID);
        if (price == null) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_DISH_PRICE);
        dishHelper.validateDescription(description);
        dishHelper.validatePrice(price);

        DishModel dish = dishPersistencePort.getDishById(dishId)
                .orElseThrow(DishNotFoundException::new);

        validateOwnership(dish.getRestaurant().getId(), DomainConstants.MOCK_OWNER_ID);

        dish.setDescription(description.trim());
        dish.setPrice(price);

        dishPersistencePort.updateDish(dish);
    }

    private void validateRestaurantExists(Long restaurantId) {
        restaurantPersistencePort.getRestaurantById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);
    }

    private void validateCategoryExists(Long categoryId) {
        categoryPersistencePort.getCategoryById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
    }

    private void validateOwnership(Long restaurantId, Long ownerId) {
        RestaurantModel restaurant = restaurantPersistencePort.getRestaurantById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);

        if (!ownerId.equals(restaurant.getOwnerId())) {
            throw new UnauthorizedUserException();
        }
    }
}