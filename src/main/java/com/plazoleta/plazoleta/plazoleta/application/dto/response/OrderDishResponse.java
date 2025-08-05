package com.plazoleta.plazoleta.plazoleta.application.dto.response;

public record OrderDishResponse(
        Long dishId,
        String dishName,
        int quantity
) {}
