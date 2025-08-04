package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;

import java.util.Optional;

public interface OrderPersistencePort {
    void saveOrder(OrderModel orderModel);
    Optional<OrderModel> getActiveOrderByClientId(Long clientId);
}
