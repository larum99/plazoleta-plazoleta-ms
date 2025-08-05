package com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql;

import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>,
        JpaSpecificationExecutor<OrderEntity> {

    List<OrderEntity> findByClientIdAndStatusIn(Long clientId, List<String> statuses);

    Optional<OrderEntity> findFirstByClientIdAndStatusIn(Long clientId, List<String> statuses);
}
