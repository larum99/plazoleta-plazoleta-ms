package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.OrderListCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.InvalidOrderStatusException;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.OrderNotFoundException;
import com.plazoleta.plazoleta.plazoleta.domain.helpers.OrderHelper;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.OrderServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.*;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;
import com.plazoleta.plazoleta.plazoleta.domain.utils.OrderStatus;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.request.TraceabilityLogRequest;

import java.time.LocalDateTime;
import java.util.List;

public class OrderUseCase implements OrderServicePort {

    private final OrderPersistencePort orderPersistencePort;
    private final OrderNotificationPort orderNotificationPort;
    private final OrderTraceabilityPort orderTraceabilityPort;
    private final OrderHelper orderHelper;

    public OrderUseCase(
            OrderPersistencePort orderPersistencePort,
            DishPersistencePort dishPersistencePort,
            RestaurantPersistencePort restaurantPersistencePort,
            EmployeeRestaurantPersistencePort employeeRestaurantPersistencePort,
            OrderNotificationPort orderNotificationPort,
            OrderTraceabilityPort orderTraceabilityPort
    ) {
        this.orderPersistencePort = orderPersistencePort;
        this.orderNotificationPort = orderNotificationPort;
        this.orderTraceabilityPort = orderTraceabilityPort;
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

        orderModel = orderPersistencePort.saveOrder(orderModel);

        sendTraceLog(orderModel, OrderStatus.PENDIENTE.name(), clientId, DomainConstants.ORDER_CREATED);
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

        order = orderPersistencePort.saveOrder(order);

        sendTraceLog(order, OrderStatus.EN_PREPARACION.name(), employeeId, DomainConstants.ORDER_ASSIGNED_PREPARATION);
    }

    @Override
    public void markOrderAsReady(Long orderId, Long employeeId, String role) {
        orderHelper.validateEmployeeRole(role);

        OrderModel order = orderPersistencePort.getOrderById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        orderHelper.validateEmployeeAssignedToOrder(order, employeeId);
        orderHelper.validateOrderIsInPreparation(order);

        order.setStatus(OrderStatus.LISTO);

        String code = orderNotificationPort.notifyClientOrderReady(order);
        order.setVerificationCode(code);

        order = orderPersistencePort.updateOrder(order);

        sendTraceLog(order, OrderStatus.LISTO.name(), employeeId, DomainConstants.ORDER_READY);
    }

    @Override
    public void markOrderAsDelivered(Long orderId, Long employeeId, String role, String code) {
        orderHelper.validateEmployeeRole(role);

        OrderModel order = orderPersistencePort.getOrderById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        orderHelper.validateEmployeeAssignedToOrder(order, employeeId);
        orderHelper.validateOrderIsReady(order);
        orderHelper.validateVerificationCode(order, code);

        order.setStatus(OrderStatus.ENTREGADO);
        order = orderPersistencePort.updateOrder(order);

        sendTraceLog(order, OrderStatus.ENTREGADO.name(), employeeId, DomainConstants.ORDER_DELIVERED);
    }

    @Override
    public void cancelOrder(Long orderId, Long userId, String role) {
        orderHelper.validateRole(role);

        OrderModel order = orderPersistencePort.getOrderById(orderId)
                .orElseThrow(OrderNotFoundException::new);

        orderHelper.validateOrderBelongsToClient(order, userId);

        if (!OrderStatus.PENDIENTE.equals(order.getStatus())) {
            orderNotificationPort.notifyClientCannotCancel(order);
            throw new InvalidOrderStatusException();
        }

        order.setStatus(OrderStatus.CANCELADO);
        order = orderPersistencePort.updateOrder(order);

        sendTraceLog(order, OrderStatus.CANCELADO.name(), userId, DomainConstants.ORDER_CANCELLED);
    }

    @Override
    public List<Long> getOrderIdsByRestaurantId(Long restaurantId, Long employeeId, String role) {

        return orderPersistencePort.findOrderIdsByRestaurantId(restaurantId);
    }


    private void sendTraceLog(OrderModel order, String newStatus, Long changedBy, String description) {
        TraceabilityLogRequest traceRequest = new TraceabilityLogRequest();
        traceRequest.setOrderId(order.getId());
        traceRequest.setStatus(newStatus);
        traceRequest.setChangedAt(LocalDateTime.now());
        traceRequest.setChangedBy(changedBy);
        traceRequest.setClientId(order.getClientId());
        traceRequest.setDescription(description);

        orderTraceabilityPort.sendTraceabilityLog(traceRequest);
    }
}
