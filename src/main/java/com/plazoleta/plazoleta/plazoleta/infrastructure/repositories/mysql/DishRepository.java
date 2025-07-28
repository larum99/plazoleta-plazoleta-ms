package com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql;

import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DishRepository extends JpaRepository<DishEntity, Long> {
    Optional<DishEntity> findByName(String name);
}
