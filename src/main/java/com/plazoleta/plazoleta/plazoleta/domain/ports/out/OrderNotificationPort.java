package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;

public interface OrderNotificationPort {
    String notifyClientOrderReady(OrderModel order);

}
