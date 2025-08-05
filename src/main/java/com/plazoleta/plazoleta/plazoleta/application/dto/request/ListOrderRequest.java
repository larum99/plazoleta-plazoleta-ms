package com.plazoleta.plazoleta.plazoleta.application.dto.request;

public record ListOrderRequest(
        Long restaurantId,
        String status,
        int page,
        int size
) {}
