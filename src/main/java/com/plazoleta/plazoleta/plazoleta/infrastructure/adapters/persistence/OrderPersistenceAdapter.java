package com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.persistence;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.OrderListCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.OrderPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.OrderStatus;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.OrderEntity;
import com.plazoleta.plazoleta.plazoleta.infrastructure.mappers.OrderEntityMapper;
import com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql.OrderRepository;
import com.plazoleta.plazoleta.plazoleta.infrastructure.specifications.OrderSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    @Override
    public PageResult<OrderModel> getOrdersByCriteria(OrderListCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());

        Specification<OrderEntity> spec = Specification
                .where(OrderSpecification.hasRestaurantId(criteria.getRestaurantId()))
                .and(OrderSpecification.hasStatus(criteria.getStatus()));

        Page<OrderEntity> page = orderRepository.findAll(spec, pageable);
        List<OrderModel> results = orderEntityMapper.entityToModelList(page.getContent());

        return new PageResult<>(
                results,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isFirst(),
                page.isLast()
        );
    }
}
