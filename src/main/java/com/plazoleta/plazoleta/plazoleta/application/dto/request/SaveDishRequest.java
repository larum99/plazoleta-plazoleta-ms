package com.plazoleta.plazoleta.plazoleta.application.dto.request;

import java.math.BigDecimal;

public record SaveDishRequest(
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        Long restaurantId,
        Long categoryId,
        Boolean active
) {
}
