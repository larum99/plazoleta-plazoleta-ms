package com.plazoleta.plazoleta.plazoleta.infrastructure.specifications;

import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.OrderEntity;
import com.plazoleta.plazoleta.plazoleta.infrastructure.utils.constants.Constants;
import org.springframework.data.jpa.domain.Specification;

public class OrderSpecification {

    private OrderSpecification() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<OrderEntity> hasRestaurantId(Long restaurantId) {
        return (root, query, cb) ->
                restaurantId == null
                        ? cb.conjunction()
                        : cb.equal(root.get(Constants.RESTAURANT).get(Constants.ID), restaurantId);
    }

    public static Specification<OrderEntity> hasStatus(String status) {
        return (root, query, cb) ->
                status == null
                        ? cb.conjunction()
                        : cb.equal(root.get(Constants.STATUS), status);
    }
}
