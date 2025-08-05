package com.plazoleta.plazoleta.plazoleta.domain.model;

public class RestaurantEmployeeModel {
    private Long id;
    private Long restaurantId;
    private Long employeeId;

    public RestaurantEmployeeModel() {
    }

    public RestaurantEmployeeModel(Long id, Long restaurantId, Long employeeId) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.employeeId = employeeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
}
