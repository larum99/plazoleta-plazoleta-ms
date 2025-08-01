package com.plazoleta.plazoleta.plazoleta.infrastructure.endpoints.rest;

import com.plazoleta.plazoleta.commons.configurations.swagger.docs.RestaurantControllerDocs.*;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.ListRestaurantRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveRestaurantRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.PagedRestaurantResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveRestaurantResponse;
import com.plazoleta.plazoleta.plazoleta.application.services.RestaurantService;
import com.plazoleta.plazoleta.plazoleta.infrastructure.utils.constants.ControllerConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstants.BASE_URL)
@RequiredArgsConstructor
@Tag(name = ControllerConstants.TAG, description = ControllerConstants.TAG_DESCRIPTION)
public class RestaurantController {

    private final RestaurantService restaurantService;

    @CreateRestaurantDocs
    @PostMapping(ControllerConstants.SAVE_PATH_RESTAURANT)
    @PreAuthorize(ControllerConstants.ROLE_ADMINISTRADOR)
    public ResponseEntity<SaveRestaurantResponse> saveRestaurant(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody SaveRestaurantRequest request) {

        String token = authorizationHeader.replace(ControllerConstants.BEARER_PREFIX, "");
        SaveRestaurantResponse response = restaurantService.saveRestaurant(request, token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @ListRestaurantsDocs
    @GetMapping(ControllerConstants.LIST_PATH_RESTAURANTS)
    public ResponseEntity<PagedRestaurantResponse> listRestaurants(
            @RequestParam(defaultValue = ControllerConstants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = ControllerConstants.DEFAULT_SIZE) int size
    ) {
        ListRestaurantRequest request = new ListRestaurantRequest(page, size);
        return ResponseEntity.ok(restaurantService.listRestaurants(request));
    }
}
