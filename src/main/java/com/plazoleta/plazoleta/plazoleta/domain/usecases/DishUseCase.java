package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
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

    public DishUseCase(
            DishPersistencePort dishPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            CategoryPersistencePort categoryPersistencePort
    ) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public void createDish(DishModel dishModel) {
        normalizeFields(dishModel);
        validateMandatoryFields(dishModel);
        validatePrice(dishModel.getPrice());

        Long restaurantId = dishModel.getRestaurant().getId();
        Long categoryId = dishModel.getCategory().getId();

        validateRestaurantExists(restaurantId);
        validateCategoryExists(categoryId);

        dishPersistencePort.saveDish(dishModel);
    }

    private void normalizeFields(DishModel model) {
        model.setName(safeTrim(model.getName()));
        model.setDescription(safeTrim(model.getDescription()));
        model.setImageUrl(safeTrim(model.getImageUrl()));
    }

    private String safeTrim(String value) {
        return value == null ? null : value.trim();
    }

    private void validateMandatoryFields(DishModel model) {
        if (isBlank(model.getName())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_DISH_NAME);
        if (model.getPrice() == null) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_DISH_PRICE);
        if (isBlank(model.getDescription())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_DISH_DESCRIPTION);

        if (model.getRestaurant() == null || model.getRestaurant().getId() == null) {
            throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_RESTAURANT_ID);
        }

        if (model.getCategory() == null || model.getCategory().getId() == null) {
            throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_CATEGORY_ID);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= DomainConstants.MIN_PRICE) {
            throw new InvalidPriceException();
        }
    }

    private void validateRestaurantExists(Long restaurantId) {
        restaurantPersistencePort.getRestaurantById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);
    }

    private void validateCategoryExists(Long categoryId) {
        categoryPersistencePort.getCategoryById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
    }
}
