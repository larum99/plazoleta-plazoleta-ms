package com.plazoleta.plazoleta.plazoleta.application.mappers;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.ListOrderRequest;
import com.plazoleta.plazoleta.plazoleta.domain.criteria.OrderListCriteria;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderCriteriaMapper {

    OrderListCriteria toCriteria(ListOrderRequest listOrderRequest);

}
