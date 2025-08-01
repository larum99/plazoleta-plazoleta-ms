package com.plazoleta.plazoleta.plazoleta.domain.helpers;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.DishCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.CategoryPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;

import java.math.BigDecimal;

public class DishHelper {

    private final DishPersistencePort dishPersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;
    private final CategoryPersistencePort categoryPersistencePort;

    public DishHelper(
            DishPersistencePort dishPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            CategoryPersistencePort categoryPersistencePort
    ) {
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
    }

    public void validateRole(String role) {
        if (!DomainConstants.ROLE_OWNER.equalsIgnoreCase(role)) {
            throw new UnauthorizedUserException();
        }
    }

    public void normalizeFields(DishModel model) {
        model.setName(safeTrim(model.getName()));
        model.setDescription(safeTrim(model.getDescription()));
        model.setImageUrl(safeTrim(model.getImageUrl()));
    }

    public void validateMandatoryFields(DishModel model) {
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

    public void validatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException();
        }
    }

    public void validateDescription(String description) {
        if (isBlank(description)) {
            throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_DISH_DESCRIPTION);
        }
    }

    public void validateRestaurantExists(Long restaurantId) {
        restaurantPersistencePort.getRestaurantById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);
    }

    public void validateCategoryExists(Long categoryId) {
        categoryPersistencePort.getCategoryById(categoryId)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public void validateOwnership(Long restaurantId, Long ownerId) {
        RestaurantModel restaurant = restaurantPersistencePort.getRestaurantById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);

        if (!ownerId.equals(restaurant.getOwnerId())) {
            throw new UnauthorizedUserException();
        }
    }

    public DishModel getValidatedDishForUpdate(Long dishId, String description, BigDecimal price, Long ownerId) {
        if (dishId == null) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_DISH_ID);
        if (price == null) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_DISH_PRICE);
        validateDescription(description);
        validatePrice(price);

        DishModel dish = dishPersistencePort.getDishById(dishId)
                .orElseThrow(DishNotFoundException::new);

        validateOwnership(dish.getRestaurant().getId(), ownerId);
        return dish;
    }

    private String safeTrim(String value) {
        return value == null ? null : value.trim();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public DishModel getValidatedDishForStatusChange(Long dishId, Long ownerId) {
        DishModel dish = dishPersistencePort.getDishById(dishId)
                .orElseThrow(DishNotFoundException::new);

        validateOwnership(dish.getRestaurant().getId(), ownerId);
        return dish;
    }

    private void validatePageNumber(int page) {
        if (page < DomainConstants.DEFAULT_PAGE_NUMBER) {
            throw new PageNumberNegativeException();
        }
    }

    private void validatePageSize(int size) {
        if (size <= DomainConstants.DEFAULT_SIZE_NUMBER) {
            throw new PageSizeInvalidException();
        }
    }

    public void validateCriteria(DishCriteria criteria) {
        if (criteria.getCategoryId() == null) {
            throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_CATEGORY_ID);
        }

        if (criteria.getRestaurantId() == null) {
            throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_RESTAURANT_ID);
        }

        validatePageNumber(criteria.getPage());
        validatePageSize(criteria.getSize());
    }
}
