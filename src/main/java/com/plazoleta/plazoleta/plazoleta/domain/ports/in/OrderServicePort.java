package com.plazoleta.plazoleta.plazoleta.domain.ports.in;

import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;

public interface OrderServicePort {
    void createOrder(OrderModel orderModel, Long clientId, String role);
}
