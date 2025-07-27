package com.plazoleta.plazoleta.plazoleta.application.mappers;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveRestaurantRequest;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantDtoMapper {
    RestaurantModel requestToModel(SaveRestaurantRequest dto);
}
