package com.plazoleta.plazoleta.plazoleta.infrastructure.specifications;

import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.DishEntity;
import com.plazoleta.plazoleta.plazoleta.infrastructure.utils.constants.Constants;
import org.springframework.data.jpa.domain.Specification;

public class DishSpecification {

    private DishSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<DishEntity> hasRestaurantId(Long restaurantId) {
        return (root, query, criteriaBuilder) ->
                restaurantId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(Constants.RESTAURANT).get(Constants.ID), restaurantId);
    }

    public static Specification<DishEntity> hasCategoryId(Long categoryId) {
        return (root, query, criteriaBuilder) ->
                categoryId == null ? criteriaBuilder.conjunction() :
                        criteriaBuilder.equal(root.get(Constants.CATEGORY).get(Constants.ID), categoryId);
    }
}