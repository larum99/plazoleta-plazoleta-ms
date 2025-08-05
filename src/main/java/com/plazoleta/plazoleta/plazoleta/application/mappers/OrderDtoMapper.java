package com.plazoleta.plazoleta.plazoleta.application.mappers;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.OrderDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.OrderDishResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.OrderResponse;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderDishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",

        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface OrderDtoMapper {

    OrderModel toOrderModelFromCreateRequest(CreateOrderRequest request);

    @Mapping(target = "dish.id", source = "dishId")
    OrderDishModel toOrderDishModel(OrderDishRequest request);

    List<OrderDishModel> toOrderDishModelList(List<OrderDishRequest> dishRequests);

    @Mapping(source = "restaurant.id", target = "restaurantId")
    @Mapping(source = "restaurant.name", target = "restaurantName")

    @Mapping(target = "dishes", source = "dishes")
    @Mapping(source = "date", target = "createdAt")
    OrderResponse modelToResponse(OrderModel model);

    @Mapping(source = "dish.id", target = "dishId")
    @Mapping(source = "dish.name", target = "dishName")
    OrderDishResponse dishModelToResponse(OrderDishModel dishModel);

    List<OrderResponse> modelListToResponseList(List<OrderModel> modelList);
}