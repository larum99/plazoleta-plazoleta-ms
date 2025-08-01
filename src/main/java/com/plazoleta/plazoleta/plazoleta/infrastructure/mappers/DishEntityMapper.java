package com.plazoleta.plazoleta.plazoleta.infrastructure.mappers;

import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.DishEntity;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", uses = {CategoryEntityMapper.class, RestaurantEntityMapper.class})
public interface DishEntityMapper {

    DishModel entityToModel(DishEntity entity);
    DishEntity modelToEntity(DishModel model);

    default DishModel optionalEntityToModel(Optional<DishEntity> optionalEntity) {
        if (optionalEntity.isPresent()) {
            DishEntity entity = optionalEntity.get();
            return entityToModel(entity);
        }
        return null;
    }

    List<DishModel> entityListToModelList(List<DishEntity> entities);
}
