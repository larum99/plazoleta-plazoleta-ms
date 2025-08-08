package com.plazoleta.plazoleta.plazoleta.domain.helpers;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderDishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.EmployeeRestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.OrderPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;
import com.plazoleta.plazoleta.plazoleta.domain.utils.OrderStatus;

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

    public void validateEmployeeRole(String role) {
        if (!DomainConstants.ROLE_EMPLOYEE.equalsIgnoreCase(role)) {
            throw new UnauthorizedUserException();
        }
    }

    public void validateEmployeeCanAssignOrder(OrderModel order, Long employeeId) {
        Long restaurantIdOfEmployee = getRestaurantIdByEmployeeId(employeeId);
        if (!restaurantIdOfEmployee.equals(order.getRestaurant().getId())) {
            throw new InvalidRestaurantAssignmentException();
        }
    }

    public void validateOrderNotAssigned(OrderModel order) {
        if (order.getAssignedEmployeeId() != null) {
            throw new OrderAlreadyAssignedException();
        }
    }

    public void validateOrderIsPending(OrderModel order) {
        if (!OrderStatus.PENDIENTE.equals(order.getStatus())) {
            throw new InvalidOrderStatusException();
        }
    }

    public void validateNewStatusIsInPreparation(String status) {
        if (!OrderStatus.EN_PREPARACION.name().equalsIgnoreCase(status)) {
            throw new InvalidOrderStatusException();
        }
    }

    public void validateEmployeeAssignedToOrder(OrderModel order, Long employeeId) {
        if (!employeeId.equals(order.getAssignedEmployeeId())) {
            throw new UnauthorizedOrderAccessException();
        }
    }

    public void validateOrderIsInPreparation(OrderModel order) {
        if (!OrderStatus.EN_PREPARACION.equals(order.getStatus())) {
            throw new InvalidOrderStatusException();
        }
    }

    public void validateOrderIsReady(OrderModel order) {
        if (!OrderStatus.LISTO.equals(order.getStatus())) {
            throw new InvalidOrderStatusException();
        }
    }

    public void validateVerificationCode(OrderModel order, String code) {
        if (order.getVerificationCode() == null || !order.getVerificationCode().equals(code)) {
            throw new InvalidVerificationCodeException();
        }
    }

}
