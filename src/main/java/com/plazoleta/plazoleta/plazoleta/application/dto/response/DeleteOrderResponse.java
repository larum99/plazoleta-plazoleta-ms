package com.plazoleta.plazoleta.plazoleta.application.dto.response;

import java.time.LocalDateTime;

public record DeleteOrderResponse(
        String message,
        LocalDateTime timestamp
) {
}
