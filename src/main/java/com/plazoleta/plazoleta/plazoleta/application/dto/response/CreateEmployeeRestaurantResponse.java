package com.plazoleta.plazoleta.plazoleta.application.dto.response;

import java.time.LocalDateTime;

public record CreateEmployeeRestaurantResponse(
        String message,
        LocalDateTime timestamp
) {}
