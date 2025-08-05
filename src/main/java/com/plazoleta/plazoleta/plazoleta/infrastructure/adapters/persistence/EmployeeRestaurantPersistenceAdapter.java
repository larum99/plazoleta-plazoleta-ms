package com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.persistence;

import com.plazoleta.plazoleta.plazoleta.domain.ports.out.EmployeeRestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.EmployeeRestaurantEntity;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.RestaurantEntity;
import com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql.EmployeeRestaurantRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EmployeeRestaurantPersistenceAdapter implements EmployeeRestaurantPersistencePort {

    private final EmployeeRestaurantRepository employeeRestaurantRepository;

    public EmployeeRestaurantPersistenceAdapter(EmployeeRestaurantRepository employeeRestaurantRepository) {
        this.employeeRestaurantRepository = employeeRestaurantRepository;
    }

    @Override
    public void saveAssociation(Long employeeId, Long restaurantId) {
        RestaurantEntity restaurant = new RestaurantEntity();
        restaurant.setId(restaurantId);

        EmployeeRestaurantEntity entity = new EmployeeRestaurantEntity();
        entity.setEmployeeId(employeeId);
        entity.setRestaurant(restaurant);

        employeeRestaurantRepository.save(entity);
    }

    @Override
    public Optional<Long> getRestaurantIdByEmployeeId(Long employeeId) {
        return employeeRestaurantRepository.findByEmployeeId(employeeId)
                .map(entity -> entity.getRestaurant().getId());
    }

    @Override
    public boolean existsAssociation(Long employeeId, Long restaurantId) {
        return employeeRestaurantRepository.existsByEmployeeIdAndRestaurant_Id(employeeId, restaurantId);
    }

}

