package com.plazoleta.plazoleta.plazoleta.application.dto.request;

public record UpdateOrderRequest(
        Long employeeId,
        String status
) {
}
