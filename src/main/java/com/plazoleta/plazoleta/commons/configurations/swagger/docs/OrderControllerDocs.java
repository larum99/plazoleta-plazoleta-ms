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
            summary = "Listar pedidos filtrados y paginados",
            description = "Obtiene una lista de pedidos del restaurante del empleado filtrados por estado, con paginación."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = "Pedidos listados correctamente",
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_LIST,
                                    summary = "Listado de pedidos",
                                    value = OrderSwaggerExamples.ORDER_LIST_RESPONSE
                            )
                    )
            ),
            @ApiResponse(responseCode = BAD_REQUEST, description = "Filtros inválidos o usuario no autorizado",
                    content = @Content(
                            mediaType = APPLICATION_JSON
                    )
            )
    })
    public @interface ListOrdersDocs {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Actualizar estado del pedido",
            description = "Permite a un empleado actualizar estado del pedido.",
            requestBody = @RequestBody(
                    description = "Nuevo estado del pedido",
                    required = true,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_UPDATE_REQUEST,
                                    summary = "Actualizar estado del pedido",
                                    value = OrderSwaggerExamples.ORDER_UPDATE_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = "Estado del pedido actualizado correctamente",
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_SUCCESS,
                                    summary = "Pedido actualizado",
                                    value = OrderSwaggerExamples.ORDER_UPDATED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(responseCode = NOT_FOUND, description = "Pedido no encontrado",
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_NOT_FOUND,
                                    summary = "No encontrado",
                                    value = OrderSwaggerExamples.ORDER_NOT_FOUND_RESPONSE
                            )
                    )
            ),
            @ApiResponse(responseCode = BAD_REQUEST, description = "Pedido ya asignado o estado inválido",
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_ERROR,
                                    summary = "Pedido ya asignado",
                                    value = OrderSwaggerExamples.ORDER_ALREADY_ASSIGNED_RESPONSE
                            )
                    )
            )
    })
    public @interface UpdateOrderDocs {}

}

