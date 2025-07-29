package com.plazoleta.plazoleta.plazoleta.domain.helpers;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;

import java.math.BigDecimal;

public class DishHelper {

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

    private String safeTrim(String value) {
        return value == null ? null : value.trim();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
