package com.plazoleta.plazoleta.plazoleta.application.services;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.ListOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.CreateOrderResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.PagedOrderResponse;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request, String token);
    PagedOrderResponse listOrdersByRestaurant(ListOrderRequest request, String token);
}
