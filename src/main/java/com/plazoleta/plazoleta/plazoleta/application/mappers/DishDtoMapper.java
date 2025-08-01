package com.plazoleta.plazoleta.plazoleta.application.mappers;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.DishResponse;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DishDtoMapper {

    @Mapping(target = "category.id", source = "categoryId")
    @Mapping(target = "restaurant.id", source = "restaurantId")
    DishModel requestToModel(SaveDishRequest request);

    @Mapping(source = "category.name", target = "category")
    @Mapping(source = "restaurant.name", target = "restaurant")
    DishResponse modelToResponse(DishModel dishModel);

    List<DishResponse> modelListToResponseList(List<DishModel> dishModels);
}
