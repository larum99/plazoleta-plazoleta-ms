package com.plazoleta.plazoleta.plazoleta.application.mappers;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.ListDishRequest;
import com.plazoleta.plazoleta.plazoleta.domain.criteria.DishCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DishCriteriaMapper {

    DishCriteria requestToCriteria(ListDishRequest listDishRequest);

}
