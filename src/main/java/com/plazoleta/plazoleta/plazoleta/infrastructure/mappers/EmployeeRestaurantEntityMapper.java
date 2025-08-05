package com.plazoleta.plazoleta.plazoleta.infrastructure.mappers;

import com.plazoleta.plazoleta.plazoleta.domain.model.EmployeeRestaurantModel;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.EmployeeRestaurantEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeRestaurantEntityMapper {

    EmployeeRestaurantModel toModel(EmployeeRestaurantEntity entity);

    EmployeeRestaurantEntity toEntity(EmployeeRestaurantModel model);
}
