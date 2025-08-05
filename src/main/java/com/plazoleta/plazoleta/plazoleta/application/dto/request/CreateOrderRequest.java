package com.plazoleta.plazoleta.plazoleta.application.dto.request;

import java.util.List;

public record CreateOrderRequest(
        List<OrderDishRequest> dishes
) {
}
