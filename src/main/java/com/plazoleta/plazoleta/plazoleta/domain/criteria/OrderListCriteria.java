package com.plazoleta.plazoleta.plazoleta.domain.criteria;

public class OrderListCriteria {

    private final Long restaurantId;
    private final String status;
    private final int page;
    private final int size;

    public OrderListCriteria(Long restaurantId, String status, int page, int size) {
        this.restaurantId = restaurantId;
        this.status = status;
        this.page = page;
        this.size = size;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public String getStatus() {
        return status;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
