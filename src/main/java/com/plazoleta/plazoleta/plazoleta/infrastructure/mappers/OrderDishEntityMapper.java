package com.plazoleta.plazoleta.plazoleta.infrastructure.mappers;

import com.plazoleta.plazoleta.plazoleta.domain.model.OrderDishModel;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.OrderDishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {DishEntityMapper.class, RestaurantEntityMapper.class})
public interface OrderDishEntityMapper {

    @Mapping(target = "order", ignore = true)
    OrderDishModel entityToModel(OrderDishEntity entity);

    @Mapping(target = "order", ignore = true)
    OrderDishEntity modelToEntity(OrderDishModel model);

    List<OrderDishModel> entityToModelList(List<OrderDishEntity> entities);

    List<OrderDishEntity> modelToEntityList(List<OrderDishModel> models);
}
