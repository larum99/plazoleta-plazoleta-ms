package com.plazoleta.plazoleta.plazoleta.domain.model;

public class EmployeeRestaurantModel {
    private Long id;
    private Long employeeId;
    private Long restaurantId;

    public EmployeeRestaurantModel() {
    }

    public EmployeeRestaurantModel(Long id, Long employeeId, Long restaurantId) {
        this.id = id;
        this.employeeId = employeeId;
        this.restaurantId = restaurantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }
}

