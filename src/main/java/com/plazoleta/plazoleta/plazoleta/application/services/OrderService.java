package com.plazoleta.plazoleta.plazoleta.application.services;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.ListOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.UpdateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.CreateOrderResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.PagedOrderResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.UpdateOrderStatusResponse;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request, String token);
    PagedOrderResponse listOrdersByRestaurant(ListOrderRequest request, String token);
    UpdateOrderStatusResponse updateOrderStatus(Long orderId, UpdateOrderRequest request, String token);

}
