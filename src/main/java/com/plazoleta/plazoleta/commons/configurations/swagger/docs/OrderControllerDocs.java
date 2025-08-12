package com.plazoleta.plazoleta.commons.configurations.swagger.docs;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateOrderRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.CreateOrderResponse;
import com.plazoleta.plazoleta.commons.configurations.swagger.examples.OrderSwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

import static com.plazoleta.plazoleta.commons.configurations.swagger.docs.SwaggerConstants.*;
import static com.plazoleta.plazoleta.commons.configurations.swagger.examples.OrderSwaggerExamples.CANCEL_ORDER_SUCCESS_RESPONSE;
import static com.plazoleta.plazoleta.plazoleta.infrastructure.utils.constants.ControllerConstants.*;

public class OrderControllerDocs {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = SUMMARY_CREATE_ORDER,
            description = DESCRIPTION_CREATE_ORDER,
            requestBody = @RequestBody(
                    description = DESCRIPTION_CREATE_ORDER,
                    required = true,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = CreateOrderRequest.class),
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_CREATE_REQUEST,
                                    summary = SUMMARY_CREATE_ORDER_EXAMPLE,
                                    value = OrderSwaggerExamples.CREATE_ORDER_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CREATED,
                    description = DESCRIPTION_CREATE_ORDER_SUCCESS,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = CreateOrderResponse.class),
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_SUCCESS,
                                    summary = SUMMARY_ORDER_CREATED,
                                    value = OrderSwaggerExamples.ORDER_CREATED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = DESCRIPTION_ORDER_VALIDATION_ERROR,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_VALIDATION_ERROR,
                                    summary = SUMMARY_ORDER_INVALID,
                                    value = OrderSwaggerExamples.INVALID_ORDER_RESPONSE
                            )
                    )
            )
    })
    public @interface CreateOrderDocs {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = SUMMARY_LIST_ORDERS,
            description = DESCRIPTION_LIST_ORDERS
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = DESCRIPTION_ORDER_LISTED,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_LIST,
                                    summary = SUMMARY_ORDER_LIST,
                                    value = OrderSwaggerExamples.ORDER_LIST_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = SUMMARY_LIST_BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON)
            )
    })
    public @interface ListOrdersDocs {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = SUMMARY_UPDATE_ORDER,
            description = DESCRIPTION_UPDATE_ORDER,
            requestBody = @RequestBody(
                    description = DESCRIPTION_UPDATE_REQUEST,
                    required = true,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = {
                                    @ExampleObject(
                                            name = EXAMPLE_NAME_UPDATE_EN_PREPARACION,
                                            summary = SUMMARY_UPDATE_NO_CODE,
                                            value = OrderSwaggerExamples.ORDER_UPDATE_REQUEST
                                    ),
                                    @ExampleObject(
                                            name = EXAMPLE_NAME_UPDATE_ENTREGADO,
                                            summary = SUMMARY_UPDATE_WITH_CODE,
                                            value = OrderSwaggerExamples.ORDER_UPDATE_WITH_CODE_REQUEST
                                    )
                            }
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = DESCRIPTION_ORDER_UPDATED_SUCCESS,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_SUCCESS,
                                    summary = SUMMARY_ORDER_UPDATED,
                                    value = OrderSwaggerExamples.ORDER_UPDATED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = DESCRIPTION_ORDER_NOT_FOUND,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_NOT_FOUND,
                                    summary = SUMMARY_ORDER_NOT_FOUND,
                                    value = OrderSwaggerExamples.ORDER_NOT_FOUND_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = DESCRIPTION_ORDER_ALREADY_ASSIGNED,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_ERROR,
                                    summary = SUMMARY_ORDER_ALREADY_ASSIGNED,
                                    value = OrderSwaggerExamples.ORDER_ALREADY_ASSIGNED_RESPONSE
                            )
                    )
            )
    })
    public @interface UpdateOrderDocs {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = SUMMARY_CANCEL_ORDER,
            description = DESCRIPTION_CANCEL_ORDER
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = DESCRIPTION_CANCEL_ORDER_SUCCESS,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = com.plazoleta.plazoleta.plazoleta.application.dto.response.DeleteOrderResponse.class),
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_CANCEL_ORDER_SUCCESS,
                                    summary = SUMMARY_CANCEL_ORDER_SUCCESS,
                                    value = CANCEL_ORDER_SUCCESS_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = DESCRIPTION_CANCEL_ORDER_BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON)
            ),
            @ApiResponse(
                    responseCode = UNAUTHORIZED,
                    description = DESCRIPTION_CANCEL_ORDER_UNAUTHORIZED,
                    content = @Content(mediaType = APPLICATION_JSON)
            ),
            @ApiResponse(
                    responseCode = NOT_FOUND,
                    description = DESCRIPTION_CANCEL_ORDER_NOT_FOUND,
                    content = @Content(mediaType = APPLICATION_JSON)
            )
    })
    public @interface CancelOrderDocs {
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = SUMMARY_GET_ORDERS_BY_RESTAURANT,
            description = DESCRIPTION_GET_ORDERS_BY_RESTAURANT
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = OK,
                    description = DESCRIPTION_GET_ORDERS_BY_RESTAURANT_SUCCESS,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_SUCCESS,
                                    summary = SUMMARY_GET_ORDERS_BY_RESTAURANT_SUCCESS,
                                    value = OrderSwaggerExamples.ORDER_IDS_BY_RESTAURANT_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = DESCRIPTION_GET_ORDERS_BY_RESTAURANT_BAD_REQUEST,
                    content = @Content(mediaType = APPLICATION_JSON)
            ),
            @ApiResponse(
                    responseCode = UNAUTHORIZED,
                    description = DESCRIPTION_GET_ORDERS_BY_RESTAURANT_UNAUTHORIZED,
                    content = @Content(mediaType = APPLICATION_JSON)
            )
    })
    public @interface GetOrdersByRestaurantDocs {
    }

}
