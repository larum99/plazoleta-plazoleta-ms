package com.plazoleta.plazoleta.plazoleta.infrastructure.mappers;

import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;

@Mapper(
        componentModel = "spring",
        uses = {
                OrderDishEntityMapper.class,
                RestaurantEntityMapper.class
        }
)
public interface OrderEntityMapper {

    @Mapping(source = "createdAt", target = "date")
    OrderModel entityToModel(OrderEntity entity);

    @Mapping(source = "date", target = "createdAt")
    OrderEntity modelToEntity(OrderModel model);

    default OrderModel optionalEntityToModel(Optional<OrderEntity> optionalEntity) {
        if (optionalEntity.isPresent()) {
            return entityToModel(optionalEntity.get());
        }
        return null;
    }

    List<OrderModel> entityToModelList(List<OrderEntity> entities);
}
