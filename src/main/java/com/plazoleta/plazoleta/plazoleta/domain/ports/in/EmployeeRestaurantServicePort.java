package com.plazoleta.plazoleta.plazoleta.domain.ports.in;

public interface EmployeeRestaurantServicePort {

    void assignEmployeeToRestaurant(Long employeeId, Long restaurantId);

    Long getRestaurantIdOfEmployee(Long employeeId);

    boolean isEmployeeOfRestaurant(Long employeeId, Long restaurantId);
}
