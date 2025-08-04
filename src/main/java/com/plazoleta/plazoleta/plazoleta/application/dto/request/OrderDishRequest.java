package com.plazoleta.plazoleta.plazoleta.application.dto.request;

public record OrderDishRequest(
        Long dishId,
        Integer quantity
) {
}
