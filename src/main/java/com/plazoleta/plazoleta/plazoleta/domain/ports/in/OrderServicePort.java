package com.plazoleta.plazoleta.plazoleta.domain.ports.in;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.OrderListCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;

public interface OrderServicePort {
    void createOrder(OrderModel orderModel, Long clientId, String role);
    PageResult<OrderModel> listOrders(OrderListCriteria criteria, String role, Long employeeId);
    void assignOrderAndChangeStatus(Long orderId, Long employeeId, String role, String status);
    void markOrderAsReady(Long orderId, Long employeeId, String role);
    void markOrderAsDelivered(Long orderId, Long employeeId, String role, String code);
    void cancelOrder(Long orderId, Long clientId, String role);
}
