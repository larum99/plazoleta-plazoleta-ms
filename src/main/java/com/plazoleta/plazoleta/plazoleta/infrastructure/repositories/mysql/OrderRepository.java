package com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql;

import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>,
        JpaSpecificationExecutor<OrderEntity> {

    List<OrderEntity> findByClientIdAndStatusIn(Long clientId, List<String> statuses);

    Optional<OrderEntity> findFirstByClientIdAndStatusIn(Long clientId, List<String> statuses);

    @Query("SELECT o.id FROM OrderEntity o WHERE o.restaurant.id = :restaurantId")
    List<Long> findOrderIdsByRestaurantId(@Param("restaurantId") Long restaurantId);
}
