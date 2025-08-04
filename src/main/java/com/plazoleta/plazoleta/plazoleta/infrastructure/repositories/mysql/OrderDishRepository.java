package com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql;

import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDishRepository extends JpaRepository<OrderDishEntity, Long> {

    List<OrderDishEntity> findByOrderId(Long orderId);
}
