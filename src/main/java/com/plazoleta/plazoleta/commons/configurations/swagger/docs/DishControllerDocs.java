package com.plazoleta.plazoleta.commons.configurations.swagger.docs;

import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.UpdateDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveDishResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.UpdateDishResponse;
import com.plazoleta.plazoleta.commons.configurations.swagger.examples.DishSwaggerExamples;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.plazoleta.plazoleta.commons.configurations.swagger.docs.SwaggerConstants.*;

public class DishControllerDocs {

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = SUMMARY_CREATE_DISH,
            description = DESCRIPTION_CREATE_DISH,
            requestBody = @RequestBody(
                    description = REQUEST_BODY_CREATE_DISH,
                    required = true,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = SaveDishRequest.class),
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_CREATE_REQUEST,
                                    summary = SUMMARY_CREATE_DISH_EXAMPLE,
                                    value = DishSwaggerExamples.CREATE_DISH_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = CREATED, description = DESCRIPTION_CREATE_DISH_SUCCESS,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = SaveDishResponse.class),
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_SUCCESS,
                                    summary = SUMMARY_DISH_CREATED,
                                    value = DishSwaggerExamples.DISH_CREATED_RESPONSE
                            )
                    )
            ),
            @ApiResponse(responseCode = BAD_REQUEST, description = DESCRIPTION_DISH_ALREADY_EXISTS,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_ERROR,
                                    summary = SUMMARY_DISH_DUPLICATE,
                                    value = DishSwaggerExamples.DISH_ALREADY_EXISTS_RESPONSE
                            )
                    )
            )
    })
    public @interface CreateDishDocs {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = SUMMARY_UPDATE_DISH,
            description = DESCRIPTION_UPDATE_DISH,
            requestBody = @RequestBody(
                    description = REQUEST_BODY_UPDATE_DISH,
                    required = true,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = UpdateDishRequest.class),
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_UPDATE_REQUEST,
                                    summary = SUMMARY_UPDATE_DISH_EXAMPLE,
                                    value = DishSwaggerExamples.UPDATE_DISH_REQUEST
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = DESCRIPTION_UPDATE_DISH_SUCCESS,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            schema = @Schema(implementation = UpdateDishResponse.class),
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_SUCCESS,
                                    summary = SUMMARY_DISH_UPDATED,
                                    value = DishSwaggerExamples.DISH_UPDATED_RESPONSE
                            )
                    )
            )
    })
    public @interface UpdateDishDocs {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = SUMMARY_UPDATE_DISH_STATUS,
            description = DESCRIPTION_UPDATE_DISH_STATUS,
            parameters = {
                    @Parameter(name = "id", description = PARAM_ID_DESCRIPTION, required = true, example = PARAM_ID_EXAMPLE),
                    @Parameter(name = "newStatus", description = PARAM_STATUS_DESCRIPTION, required = true, example = PARAM_STATUS_EXAMPLE)
            }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = BAD_REQUEST, description = DESCRIPTION_INVALID_OR_UNAUTHORIZED,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_ERROR,
                                    summary = SUMMARY_UNAUTHORIZED_OR_INVALID,
                                    value = DishSwaggerExamples.UNAUTHORIZED_DISH_MODIFICATION_RESPONSE
                            )
                    )
            ),
            @ApiResponse(responseCode = NOT_FOUND, description = DESCRIPTION_DISH_NOT_FOUND,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_NOT_FOUND,
                                    summary = SUMMARY_ID_NOT_FOUND,
                                    value = DishSwaggerExamples.DISH_NOT_FOUND_RESPONSE
                            )
                    )
            )
    })
    public @interface UpdateDishStatusDocs {}

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @Operation(
            summary = SUMMARY_LIST_DISHES,
            description = DESCRIPTION_LIST_DISHES
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = OK, description = DESCRIPTION_LIST_DISHES_SUCCESS,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_LIST,
                                    summary = SUMMARY_DISH_LIST,
                                    value = DishSwaggerExamples.DISH_LIST_RESPONSE
                            )
                    )
            ),
            @ApiResponse(responseCode = BAD_REQUEST, description = DESCRIPTION_BAD_REQUEST_MISSING_FILTERS,
                    content = @Content(
                            mediaType = APPLICATION_JSON,
                            examples = @ExampleObject(
                                    name = EXAMPLE_NAME_ERROR,
                                    summary = SUMMARY_MISSING_FILTERS,
                                    value = DishSwaggerExamples.MISSING_FILTERS_RESPONSE
                            )
                    )
            )
    })
    public @interface ListDishesDocs {}

}