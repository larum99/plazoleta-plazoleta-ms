package com.plazoleta.plazoleta.plazoleta.domain.criteria;

public class RestaurantCriteria {

    private final int page;
    private final int size;

    public RestaurantCriteria(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
