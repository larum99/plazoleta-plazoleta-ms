package com.plazoleta.plazoleta.plazoleta.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Long clientId,
        Long restaurantId,
        String restaurantName,
        String status,
        LocalDateTime createdAt,
        List<OrderDishResponse> dishes
) {}
