package com.plazoleta.plazoleta.plazoleta.application.dto.response;

import java.util.List;

public record PagedOrderResponse(
        List<OrderResponse> content,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize,
        boolean isFirst,
        boolean isLast
) {}
