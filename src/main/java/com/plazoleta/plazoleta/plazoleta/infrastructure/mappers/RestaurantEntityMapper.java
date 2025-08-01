package com.plazoleta.plazoleta.plazoleta.infrastructure.mappers;

import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface RestaurantEntityMapper {

    @Mapping(source = "phoneNumber", target = "phone")
    RestaurantModel entityToModel(RestaurantEntity restaurantEntity);

    @Mapping(source = "phone", target = "phoneNumber")
    RestaurantEntity modelToEntity(RestaurantModel restaurantModel);

    default RestaurantModel optionalEntityToModel(Optional<RestaurantEntity> optionalEntity) {
        if (optionalEntity.isPresent()) {
            RestaurantEntity entity = optionalEntity.get();
            return entityToModel(entity);
        }
        return null;
    }

    List<RestaurantModel> entityToModelList(List<RestaurantEntity> entities);

    List<RestaurantEntity> modelToEntityList(List<RestaurantModel> models);

}
