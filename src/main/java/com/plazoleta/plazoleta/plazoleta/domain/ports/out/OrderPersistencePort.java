package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.OrderListCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;

import java.util.Optional;

public interface OrderPersistencePort {
    void saveOrder(OrderModel orderModel);
    Optional<OrderModel> getActiveOrderByClientId(Long clientId);
    PageResult<OrderModel> getOrdersByCriteria(OrderListCriteria criteria);
    Optional<OrderModel> getOrderById(Long orderId);
    void updateOrder(OrderModel orderModel);
}
