package com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql;

import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.EmployeeRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRestaurantRepository extends JpaRepository<EmployeeRestaurantEntity, Long> {

    boolean existsByEmployeeIdAndRestaurant_Id(Long employeeId, Long restaurantId);

    Optional<EmployeeRestaurantEntity> findByEmployeeId(Long employeeId);
}
