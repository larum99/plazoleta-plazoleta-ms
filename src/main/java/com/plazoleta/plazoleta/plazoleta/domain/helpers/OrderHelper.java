package com.plazoleta.plazoleta.plazoleta.domain.helpers;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderDishModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.EmployeeRestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.OrderPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;

import java.util.List;

public class OrderHelper {

    private final OrderPersistencePort orderPersistencePort;
    private final DishPersistencePort dishPersistencePort;
    private final RestaurantPersistencePort restaurantPersistencePort;
    private final EmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;

    public OrderHelper(
            OrderPersistencePort orderPersistencePort,
            DishPersistencePort dishPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            EmployeeRestaurantPersistencePort employeeRestaurantPersistencePort
    ) {
        this.orderPersistencePort = orderPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;

    }

    public void validateRole(String role) {
        if (!DomainConstants.ROLE_CLIENT.equalsIgnoreCase(role)) {
            throw new UnauthorizedUserException();
        }
    }

    public void validateNoActiveOrder(Long clientId) {
        orderPersistencePort.getActiveOrderByClientId(clientId).ifPresent(active -> {
            throw new ClientHasActiveOrderException();
        });
    }

    public void validateRestaurantExistence(Long restaurantId) {
        if (restaurantId == null) {
            throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_RESTAURANT_ID);
        }

        restaurantPersistencePort.getRestaurantById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);
    }

    public void validateDishesExistAndBelongToSameRestaurant(List<OrderDishModel> dishes, Long restaurantId) {
        if (dishes == null || dishes.isEmpty()) {
            throw new MissingFieldException(DomainConstants.ORDER_MISSING_DISHES);
        }

        for (OrderDishModel dish : dishes) {
            DishModel dishModel = dishPersistencePort.getDishById(dish.getDish().getId())
                    .orElseThrow(DishNotFoundException::new);

            if (!dishModel.getRestaurant().getId().equals(restaurantId)) {
                throw new DishDoesNotBelongToRestaurantException();
            }
        }
    }

    public Long getRestaurantIdByEmployeeId(Long employeeId) {
        return employeeRestaurantPersistencePort.getRestaurantIdByEmployeeId(employeeId)
                .orElseThrow(ForbiddenException::new);
    }

}
