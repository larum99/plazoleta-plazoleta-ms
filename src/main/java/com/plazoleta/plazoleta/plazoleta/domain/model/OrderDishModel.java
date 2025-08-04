package com.plazoleta.plazoleta.plazoleta.domain.model;

public class OrderDishModel {

    private Long id;
    private OrderModel order;
    private DishModel dish;
    private Integer quantity;

    public OrderDishModel() {
    }

    public OrderDishModel(Long id, OrderModel order, DishModel dish, Integer quantity) {
        this.id = id;
        this.order = order;
        this.dish = dish;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public DishModel getDish() {
        return dish;
    }

    public void setDish(DishModel dish) {
        this.dish = dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
