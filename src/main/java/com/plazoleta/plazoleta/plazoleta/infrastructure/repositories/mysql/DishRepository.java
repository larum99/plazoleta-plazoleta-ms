package com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql;

import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface DishRepository extends JpaRepository<DishEntity, Long>, JpaSpecificationExecutor<DishEntity> {
    Optional<DishEntity> findByName(String name);
}
