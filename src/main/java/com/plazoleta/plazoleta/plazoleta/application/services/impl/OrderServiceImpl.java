package com.plazoleta.plazoleta.plazoleta.application.services.impl;

import com.plazoleta.plazoleta.commons.configurations.utils.Constants;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.CreateOrderResponse;
import com.plazoleta.plazoleta.plazoleta.application.mappers.OrderDtoMapper;
import com.plazoleta.plazoleta.plazoleta.application.services.OrderService;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderDishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.OrderServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.RoleValidatorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderServicePort orderServicePort;
    private final OrderDtoMapper orderDtoMapper;
    private final RoleValidatorPort roleValidatorPort;

    @Override
    public CreateOrderResponse createOrder(CreateOrderRequest request, String token) {
        Long clientId = roleValidatorPort.extractUserId(token);
        String role = roleValidatorPort.extractRole(token);

        OrderModel order = orderDtoMapper.toOrderModelFromCreateRequest(request);
        order.setClientId(clientId);

        List<OrderDishModel> orderDishes = orderDtoMapper.toOrderDishModelList(request.dishes());
        order.setDishes(orderDishes);

        orderServicePort.createOrder(order, clientId, role);

        return new CreateOrderResponse(Constants.CREATE_ORDER_SUCCESS_MESSAGE, LocalDateTime.now());
    }

}
