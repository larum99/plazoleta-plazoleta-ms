package com.plazoleta.plazoleta.plazoleta.application.services;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.ListOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.UpdateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.CreateOrderResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.DeleteOrderResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.PagedOrderResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.UpdateOrderStatusResponse;

import java.util.List;

public interface OrderService {
    CreateOrderResponse createOrder(CreateOrderRequest request, String token);
    PagedOrderResponse listOrdersByRestaurant(ListOrderRequest request, String token);
    UpdateOrderStatusResponse updateOrderStatus(Long orderId, UpdateOrderRequest request, String token);
    DeleteOrderResponse cancelOrder(Long orderId, String token);
    List<Long> getOrderIdsByRestaurantId(Long restaurantId, String token);
}
