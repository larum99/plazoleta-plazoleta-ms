package com.plazoleta.plazoleta.commons.configurations.swagger.docs;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.UpdateDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveDishResponse;
import com.plazoleta.plazoleta.commons.configurations.swagger.examples.DishSwaggerExamples;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.UpdateDishResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

public class DishControllerDocs {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Crear un nuevo plato",
            description = "Permite registrar un nuevo plato en la plataforma.",
            requestBody = @RequestBody(
                    description = "Datos del plato a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveDishRequest.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de creación",
                                    summary = "Plato nuevo",
                                    value = DishSwaggerExamples.CREATE_DISH_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plato creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveDishResponse.class),
                            examples = @ExampleObject(
                                    name = "Respuesta exitosa",
                                    summary = "Plato creado",
                                    value = DishSwaggerExamples.DISH_CREATED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o plato ya existente",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Error",
                                    summary = "Plato duplicado",
                                    value = DishSwaggerExamples.DISH_ALREADY_EXISTS_RESPONSE
                            )
                    )
            )
    })
    public @interface CreateDishDocs {}

    @Operation(
            summary = "Actualizar un plato existente",
            description = "Permite modificar el precio y la descripción de un plato, si pertenece al restaurante del propietario.",
            requestBody = @RequestBody(
                    description = "Datos del plato a actualizar",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateDishRequest.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de actualización",
                                    summary = "Actualización parcial de plato",
                                    value = DishSwaggerExamples.UPDATE_DISH_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plato actualizado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UpdateDishResponse.class),
                            examples = @ExampleObject(
                                    name = "Respuesta exitosa",
                                    summary = "Actualización correcta",
                                    value = DishSwaggerExamples.DISH_UPDATED_RESPONSE
                            )
                    )
            ),
    })
    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface UpdateDishDocs {}

}
