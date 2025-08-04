package com.plazoleta.plazoleta.plazoleta.application.mappers;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.OrderDishRequest;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderDishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderDtoMapper {

    @Mapping(target = "restaurant.id", source = "restaurantId")
    OrderModel toOrderModelFromCreateRequest(CreateOrderRequest request);
    List<OrderDishModel> toOrderDishModelList(List<OrderDishRequest> dishRequests);
    @Mapping(target = "dish.id", source = "dishId")
    OrderDishModel toOrderDishModel(OrderDishRequest request);
}
