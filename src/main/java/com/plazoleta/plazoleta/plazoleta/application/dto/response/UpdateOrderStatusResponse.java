package com.plazoleta.plazoleta.plazoleta.application.dto.response;

import java.time.LocalDateTime;

public record UpdateOrderStatusResponse(
        String message,
        LocalDateTime timestamp
) {}
