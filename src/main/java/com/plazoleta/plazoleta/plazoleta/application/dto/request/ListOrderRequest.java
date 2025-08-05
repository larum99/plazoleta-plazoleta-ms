package com.plazoleta.plazoleta.plazoleta.application.dto.request;

public record ListOrderRequest(
        String status,
        int page,
        int size
) {}
