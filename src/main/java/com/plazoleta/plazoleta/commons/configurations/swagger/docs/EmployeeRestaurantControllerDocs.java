package com.plazoleta.plazoleta.commons.configurations.swagger.docs;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateEmployeeRestaurantRequest;
import com.plazoleta.plazoleta.commons.configurations.swagger.examples.EmployeeRestaurantSwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.plazoleta.plazoleta.commons.configurations.swagger.docs.SwaggerConstants.*;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class EmployeeRestaurantControllerDocs {

    @Target({METHOD})
    @Retention(RUNTIME)
    @Operation(
            summary = "Asociar empleado a restaurante",
            description = "Permite al propietario asociar un empleado a su restaurante.",
            requestBody = @RequestBody(
                    description = "Información del empleado a asociar",
                    required = true,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = CreateEmployeeRestaurantRequest.class),
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_CREATE_REQUEST,
                                    summary = "Ejemplo de asociación de empleado",
                                    value = EmployeeRestaurantSwaggerExamples.CREATE_EMPLOYEE_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CREATED,
                    description = "Empleado asociado correctamente al restaurante"
            ),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = "Solicitud inválida"
            ),
    })
    public @interface CreateEmployeeRestaurantDocs {}
}
