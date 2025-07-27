package com.plazoleta.plazoleta.commons.configurations.swagger.docs;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveRestaurantRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveRestaurantResponse;
import com.plazoleta.plazoleta.commons.configurations.swagger.examples.RestaurantSwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

public class RestaurantControllerDocs {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = "Crear un nuevo restaurante",
            description = "Permite registrar un nuevo restaurante en la plataforma.",
            requestBody = @RequestBody(
                    description = "Datos del restaurante a crear",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveRestaurantRequest.class),
                            examples = @ExampleObject(
                                    name = "Ejemplo de creación",
                                    summary = "Restaurante nuevo",
                                    value = RestaurantSwaggerExamples.CREATE_RESTAURANT_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurante creado exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = SaveRestaurantResponse.class),
                            examples = @ExampleObject(
                                    name = "Respuesta exitosa",
                                    summary = "Restaurante creado",
                                    value = RestaurantSwaggerExamples.RESTAURANT_CREATED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o restaurante ya existente",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "Error",
                                    summary = "Restaurante duplicado",
                                    value = RestaurantSwaggerExamples.RESTAURANT_ALREADY_EXISTS_RESPONSE
                            )
                    )
            )
    })
    public @interface CreateRestaurantDocs {
    }
}
