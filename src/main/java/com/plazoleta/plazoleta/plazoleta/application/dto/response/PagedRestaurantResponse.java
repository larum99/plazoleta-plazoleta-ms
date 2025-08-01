package com.plazoleta.plazoleta.plazoleta.application.dto.response;

import java.util.List;

public record PagedRestaurantResponse(
        List<RestaurantSummaryResponse> content,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize,
        boolean isFirst,
        boolean isLast
) {}
