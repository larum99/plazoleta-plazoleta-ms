package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.OrderListCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.helpers.OrderHelper;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.OrderServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.EmployeeRestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.OrderPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.OrderStatus;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;

import java.time.LocalDateTime;

public class OrderUseCase implements OrderServicePort {

    private final OrderPersistencePort orderPersistencePort;
    private final OrderHelper orderHelper;

    public OrderUseCase(
            OrderPersistencePort orderPersistencePort,
            DishPersistencePort dishPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            EmployeeRestaurantPersistencePort employeeRestaurantPersistencePort

    ) {
        this.orderPersistencePort = orderPersistencePort;
        this.orderHelper = new OrderHelper(orderPersistencePort, dishPersistencePort, restaurantPersistencePort, employeeRestaurantPersistencePort);
    }

    @Override
    public void createOrder(OrderModel orderModel, Long clientId, String role) {
        orderHelper.validateRole(role);
        orderHelper.validateNoActiveOrder(clientId);
        orderHelper.validateRestaurantExistence(orderModel.getRestaurant().getId());
        orderHelper.validateDishesExistAndBelongToSameRestaurant(
                orderModel.getDishes(),
                orderModel.getRestaurant().getId()
        );

        orderModel.setClientId(clientId);
        orderModel.setStatus(OrderStatus.PENDIENTE);
        orderModel.setDate(LocalDateTime.now());

        orderPersistencePort.saveOrder(orderModel);
    }

    @Override
    public PageResult<OrderModel> listOrders(OrderListCriteria criteria, String role, Long employeeId) {
        Long restaurantId = orderHelper.getRestaurantIdByEmployeeId(employeeId);
        criteria = new OrderListCriteria(
                restaurantId,
                criteria.getStatus(),
                criteria.getPage(),
                criteria.getSize()
        );
        return orderPersistencePort.getOrdersByCriteria(criteria);
    }
}
