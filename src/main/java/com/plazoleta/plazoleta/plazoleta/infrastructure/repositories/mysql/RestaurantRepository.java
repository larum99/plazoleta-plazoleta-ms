package com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql;

import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findByNit(String nit);
    Optional<RestaurantEntity> findByName(String name);
}
