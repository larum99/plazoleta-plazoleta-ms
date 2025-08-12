package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.OrderListCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;

import java.util.List;
import java.util.Optional;

public interface OrderPersistencePort {
    OrderModel  saveOrder(OrderModel orderModel);
    Optional<OrderModel> getActiveOrderByClientId(Long clientId);
    PageResult<OrderModel> getOrdersByCriteria(OrderListCriteria criteria);
    Optional<OrderModel> getOrderById(Long orderId);
    OrderModel  updateOrder(OrderModel orderModel);
    List<Long> findOrderIdsByRestaurantId(Long restaurantId);
}
