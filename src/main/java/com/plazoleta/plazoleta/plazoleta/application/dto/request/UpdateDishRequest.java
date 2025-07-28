package com.plazoleta.plazoleta.plazoleta.application.dto.request;

import java.math.BigDecimal;

public record UpdateDishRequest(
        String description,
        BigDecimal price
) {
}
