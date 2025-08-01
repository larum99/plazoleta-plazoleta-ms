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

import static com.plazoleta.plazoleta.commons.configurations.swagger.docs.SwaggerConstants.*;

public class RestaurantControllerDocs {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = SUMMARY_CREATE_RESTAURANT,
            description = DESCRIPTION_CREATE_RESTAURANT,
            requestBody = @RequestBody(
                    description = DESCRIPTION_CREATE_RESTAURANT,
                    required = true,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = SaveRestaurantRequest.class),
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_CREATE_REQUEST,
                                    summary = SUMMARY_CREATE_RESTAURANT_EXAMPLE,
                                    value = RestaurantSwaggerExamples.CREATE_RESTAURANT_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = CREATED,
                    description = DESCRIPTION_CREATE_RESTAURANT_SUCCESS,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = SaveRestaurantResponse.class),
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_SUCCESS,
                                    summary = SUMMARY_RESTAURANT_CREATED,
                                    value = RestaurantSwaggerExamples.RESTAURANT_CREATED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = BAD_REQUEST,
                    description = DESCRIPTION_RESTAURANT_ALREADY_EXISTS,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_VALIDATION_ERROR,
                                    summary = SUMMARY_RESTAURANT_DUPLICATE,
                                    value = RestaurantSwaggerExamples.RESTAURANT_ALREADY_EXISTS_RESPONSE
                            )
                    )
            )
    })
    public @interface CreateRestaurantDocs {
    }

    @Operation(
            summary = SwaggerConstants.SUMMARY_LIST_RESTAURANTS,
            description = SwaggerConstants.DESCRIPTION_LIST_RESTAURANTS,
            responses = {
                    @ApiResponse(
                            responseCode = SwaggerConstants.OK,
                            description = SwaggerConstants.RESPONSE_LIST_RESTAURANTS_SUCCESS,
                            content = @Content(
                                    mediaType = SwaggerConstants.APPLICATION_JSON,
                                    examples = @ExampleObject(
                                            name = SwaggerConstants.EXAMPLE_NAME_SUCCESS,
                                            value = RestaurantSwaggerExamples.LIST_RESTAURANTS_RESPONSE
                                    )
                            )
                    )
            }
    )
    @Target({ ElementType.METHOD })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface ListRestaurantsDocs {}

}
