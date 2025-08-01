package com.plazoleta.plazoleta.plazoleta.domain.criteria;

public class DishCriteria {

    private Long restaurantId;
    private Long categoryId;
    private int page;
    private int size;

    public DishCriteria() {
    }

    public DishCriteria(Long restaurantId, Long categoryId, int page, int size) {
        this.restaurantId = restaurantId;
        this.categoryId = categoryId;
        this.page = page;
        this.size = size;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
