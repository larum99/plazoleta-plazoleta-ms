package com.plazoleta.plazoleta.plazoleta.domain.model;

import com.plazoleta.plazoleta.plazoleta.domain.utils.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class OrderModel {

    private Long id;
    private Long clientId;
    private RestaurantModel restaurant;
    private List<OrderDishModel> dishes;
    private OrderStatus status;
    private LocalDateTime date;

    public OrderModel() {
    }

    public OrderModel(Long id, Long clientId, RestaurantModel restaurant, List<OrderDishModel> dishes,
                      OrderStatus status, LocalDateTime date) {
        this.id = id;
        this.clientId = clientId;
        this.restaurant = restaurant;
        this.dishes = dishes;
        this.status = status;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }

    public List<OrderDishModel> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDishModel> dishes) {
        this.dishes = dishes;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}