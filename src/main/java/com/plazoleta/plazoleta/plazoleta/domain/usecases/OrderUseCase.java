package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.OrderListCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.OrderNotFoundException;
import com.plazoleta.plazoleta.plazoleta.domain.helpers.OrderHelper;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.OrderServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.*;
import com.plazoleta.plazoleta.plazoleta.domain.utils.OrderStatus;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;

import java.time.LocalDateTime;

public class OrderUseCase implements OrderServicePort {

    private final OrderPersistencePort orderPersistencePort;
    private final OrderNotificationPort orderNotificationPort;
    private final OrderHelper orderHelper;

    public OrderUseCase(
            OrderPersistencePort orderPersistencePort,
            DishPersistencePort dishPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            EmployeeRestaurantPersistencePort employeeRestaurantPersistencePort,
            OrderNotificationPort orderNotificationPort
    ) {
        this.orderPersistencePort = orderPersistencePort;
        this.orderNotificationPort = orderNotificationPort;
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
        orderHelper.validateEmployeeRole(role);

        Long restaurantId = orderHelper.getRestaurantIdByEmployeeId(employeeId);
        criteria = new OrderListCriteria(
                restaurantId,
                criteria.getStatus(),
                criteria.getPage(),
                criteria.getSize()
        );
        return orderPersistencePort.getOrdersByCriteria(criteria);
    }

    @Override
    public void assignOrderAndChangeStatus(Long orderId, Long employeeId, String role, String status) {
        orderHelper.validateEmployeeRole(role);

        OrderModel order = orderPersistencePort.getOrderById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        orderHelper.validateEmployeeCanAssignOrder(order, employeeId);
        orderHelper.validateOrderNotAssigned(order);
        orderHelper.validateOrderIsPending(order);
        orderHelper.validateNewStatusIsInPreparation(status);

        order.setAssignedEmployeeId(employeeId);
        order.setStatus(OrderStatus.EN_PREPARACION);

        orderPersistencePort.saveOrder(order);
    }

    @Override
    public void markOrderAsReady(Long orderId, Long employeeId, String role) {
        orderHelper.validateEmployeeRole(role);

        OrderModel order = orderPersistencePort.getOrderById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        orderHelper.validateEmployeeAssignedToOrder(order, employeeId);
        orderHelper.validateOrderIsInPreparation(order);

        order.setStatus(OrderStatus.LISTO);
        orderPersistencePort.updateOrder(order);

        orderNotificationPort.notifyClientOrderReady(order);
    }
}
