package com.plazoleta.plazoleta.plazoleta.infrastructure.endpoints.rest;

import com.plazoleta.plazoleta.commons.configurations.swagger.docs.OrderControllerDocs.*;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.ListOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.UpdateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.CreateOrderResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.PagedOrderResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.UpdateOrderStatusResponse;
import com.plazoleta.plazoleta.plazoleta.application.services.OrderService;
import com.plazoleta.plazoleta.plazoleta.infrastructure.utils.constants.ControllerConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstants.BASE_URL)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @CreateOrderDocs
    @PostMapping(ControllerConstants.SAVE_ORDER_PATH)
    @PreAuthorize(ControllerConstants.ROLE_CLIENT)
    public ResponseEntity<CreateOrderResponse> createOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody CreateOrderRequest request
    ) {
        String token = authorizationHeader.replace(ControllerConstants.BEARER_PREFIX, "");
        CreateOrderResponse response = orderService.createOrder(request, token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ListOrdersDocs
    @GetMapping(ControllerConstants.LIST_ORDERS_PATH)
    @PreAuthorize(ControllerConstants.ROLE_EMPLOYEE)
    public ResponseEntity<PagedOrderResponse> getOrdersByCriteria(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = ControllerConstants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = ControllerConstants.DEFAULT_SIZE) int size
    ) {
        String token = authorizationHeader.replace(ControllerConstants.BEARER_PREFIX, "");
        ListOrderRequest request = new ListOrderRequest(status, page, size);
        PagedOrderResponse response = orderService.listOrdersByRestaurant(request, token);
        return ResponseEntity.ok(response);
    }

    @UpdateOrderDocs
    @PutMapping(ControllerConstants.UPDATE_ORDER_STATUS_PATH)
    @PreAuthorize(ControllerConstants.ROLE_EMPLOYEE)
    public ResponseEntity<UpdateOrderStatusResponse> updateOrderStatus(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable Long orderId,
            @RequestBody UpdateOrderRequest request
    ) {
        String token = authorizationHeader.replace(ControllerConstants.BEARER_PREFIX, "");
        UpdateOrderStatusResponse response = orderService.updateOrderStatus(orderId, request, token);
        return ResponseEntity.ok(response);
    }

}
