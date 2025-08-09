package com.plazoleta.plazoleta.plazoleta.application.services.impl;

import com.plazoleta.plazoleta.commons.configurations.utils.Constants;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.ListOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.UpdateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.*;
import com.plazoleta.plazoleta.plazoleta.application.mappers.OrderCriteriaMapper;
import com.plazoleta.plazoleta.plazoleta.application.mappers.OrderDtoMapper;
import com.plazoleta.plazoleta.plazoleta.application.services.OrderService;
import com.plazoleta.plazoleta.plazoleta.domain.criteria.OrderListCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderDishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.OrderServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.RoleValidatorPort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;
import com.plazoleta.plazoleta.plazoleta.domain.utils.OrderStatus;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;
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
    private final OrderCriteriaMapper orderCriteriaMapper;

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

    @Override
    public PagedOrderResponse listOrdersByRestaurant(ListOrderRequest request, String token) {
        Long employeeId = roleValidatorPort.extractUserId(token);
        String role = roleValidatorPort.extractRole(token);

        OrderListCriteria criteria = orderCriteriaMapper.toCriteria(request);
        PageResult<OrderModel> result = orderServicePort.listOrders(criteria, role, employeeId);

        List<OrderResponse> responses = orderDtoMapper.modelListToResponseList(result.getContent());

        return new PagedOrderResponse(
                responses,
                result.getTotalElements(),
                result.getTotalPages(),
                result.getCurrentPage(),
                result.getPageSize(),
                result.isFirst(),
                result.isLast()
        );
    }

    @Override
    public UpdateOrderStatusResponse updateOrderStatus(Long orderId, UpdateOrderRequest request, String token) {
        String role = roleValidatorPort.extractRole(token);
        Long employeeId = roleValidatorPort.extractUserId(token);

        try {
            OrderStatus status = OrderStatus.valueOf(request.status().toUpperCase());

            switch (status) {
                case EN_PREPARACION:
                    orderServicePort.assignOrderAndChangeStatus(orderId, employeeId, role, status.name());
                    break;

                case LISTO:
                    orderServicePort.markOrderAsReady(orderId, employeeId, role);
                    break;

                case ENTREGADO:
                    orderServicePort.markOrderAsDelivered(orderId, employeeId, role, request.code());
                    break;

                default:
                    throw new IllegalArgumentException(Constants.ERROR_UNSUPPORTED_ORDER_STATUS + status);
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException(Constants.ERROR_INVALID_ORDER_STATUS + request.status());
        }

        return new UpdateOrderStatusResponse(Constants.UPDATE_ORDER_STATUS_SUCCESS_MESSAGE, LocalDateTime.now());
    }

    @Override
    public DeleteOrderResponse cancelOrder(Long orderId, String token) {
        Long clientId = roleValidatorPort.extractUserId(token);
        String role = roleValidatorPort.extractRole(token);

        orderServicePort.cancelOrder(orderId, clientId, role);
        return new DeleteOrderResponse(Constants.CANCEL_ORDER_SUCCESS_MESSAGE, LocalDateTime.now());
    }
}
