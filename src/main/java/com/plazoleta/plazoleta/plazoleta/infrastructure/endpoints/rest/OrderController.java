package com.plazoleta.plazoleta.plazoleta.infrastructure.endpoints.rest;

import com.plazoleta.plazoleta.commons.configurations.swagger.docs.OrderControllerDocs.*;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.CreateOrderResponse;
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
    @PreAuthorize(ControllerConstants.ROLE_CLIENTE)
    public ResponseEntity<CreateOrderResponse> createOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody CreateOrderRequest request
    ) {
        String token = authorizationHeader.replace(ControllerConstants.BEARER_PREFIX, "");
        CreateOrderResponse response = orderService.createOrder(request, token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
