package com.plazoleta.plazoleta.plazoleta.application.dto.response;

import java.math.BigDecimal;

public record DishResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        String category,
        String restaurant
) {
}
