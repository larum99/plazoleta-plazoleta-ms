package com.plazoleta.plazoleta.plazoleta.application.dto.response;

import java.time.LocalDateTime;

public record UpdateDishStatusResponse(
        String message,
        LocalDateTime timestamp
) {}

