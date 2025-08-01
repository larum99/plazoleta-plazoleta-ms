package com.plazoleta.plazoleta.plazoleta.application.dto.request;

public record ListDishRequest(
        Long restaurantId,
        Long categoryId,
        int page,
        int size
) {}
