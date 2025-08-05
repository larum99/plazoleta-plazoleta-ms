package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import java.util.Optional;

public interface EmployeeRestaurantPersistencePort {
    void saveAssociation(Long employeeId, Long restaurantId);
    Optional<Long> getRestaurantIdByEmployeeId(Long employeeId);
    boolean existsAssociation(Long employeeId, Long restaurantId);
}
