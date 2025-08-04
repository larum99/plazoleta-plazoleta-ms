package com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.persistence;

import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.OrderPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.OrderStatus;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.OrderEntity;
import com.plazoleta.plazoleta.plazoleta.infrastructure.mappers.OrderEntityMapper;
import com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderPersistenceAdapter implements OrderPersistencePort {

    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    @Override
    public void saveOrder(OrderModel orderModel) {
        OrderEntity entity = orderEntityMapper.modelToEntity(orderModel);

        if (entity.getDishes() != null) {
            entity.getDishes().forEach(dish -> dish.setOrder(entity));
        }

        orderRepository.save(entity);
    }

    @Override
    public Optional<OrderModel> getActiveOrderByClientId(Long clientId) {
        List<String> activeStatuses = Arrays.asList(
                OrderStatus.PENDIENTE.name(),
                OrderStatus.EN_PREPARACION.name()
        );

        Optional<OrderEntity> optionalEntity = orderRepository.findFirstByClientIdAndStatusIn(clientId, activeStatuses);

        if (optionalEntity.isPresent()) {
            return Optional.of(orderEntityMapper.entityToModel(optionalEntity.get()));
        }

        return Optional.empty();
    }

    public List<OrderModel> getOrdersByClientIdAndStatuses(Long clientId, List<String> statuses) {
        List<OrderEntity> entities = orderRepository.findByClientIdAndStatusIn(clientId, statuses);
        return orderEntityMapper.entityToModelList(entities);
    }
}
