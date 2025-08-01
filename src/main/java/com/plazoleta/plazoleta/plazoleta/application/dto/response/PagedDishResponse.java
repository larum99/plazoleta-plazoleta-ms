package com.plazoleta.plazoleta.plazoleta.application.dto.response;

import java.util.List;

public record PagedDishResponse(
        List<DishResponse> content,
        long totalElements,
        int totalPages,
        int currentPage,
        int pageSize,
        boolean isFirst,
        boolean isLast
) {}
