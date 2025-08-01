package com.plazoleta.plazoleta.plazoleta.infrastructure.endpoints.rest;

import com.plazoleta.plazoleta.commons.configurations.swagger.docs.DishControllerDocs.*;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.ListDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.UpdateDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.PagedDishResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveDishResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.UpdateDishResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.UpdateDishStatusResponse;
import com.plazoleta.plazoleta.plazoleta.application.services.DishService;
import com.plazoleta.plazoleta.plazoleta.domain.criteria.DishCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;
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
@Tag(name = ControllerConstants.TAG_DISH, description = ControllerConstants.TAG_DISH_DESCRIPTION)
public class DishController {

    private final DishService dishService;

    @CreateDishDocs
    @PostMapping(ControllerConstants.SAVE_DISH_PATH)
    @PreAuthorize(ControllerConstants.ROLE_PROPIETARIO)
    public ResponseEntity<SaveDishResponse> saveDish(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody SaveDishRequest request
    ) {
        String token = authorizationHeader.replace(ControllerConstants.BEARER_PREFIX, "");
        SaveDishResponse response = dishService.saveDish(request, token);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @UpdateDishDocs
    @PutMapping(ControllerConstants.UPDATE_DISH_PATH)
    @PreAuthorize(ControllerConstants.ROLE_PROPIETARIO)
    public ResponseEntity<UpdateDishResponse> updateDish(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable Long id,
            @RequestBody UpdateDishRequest request
    ) {
        String token = authorizationHeader.replace(ControllerConstants.BEARER_PREFIX, "");
        UpdateDishResponse response = dishService.updateDish(id, request, token);
        return ResponseEntity.ok(response);
    }

    @UpdateDishStatusDocs
    @PutMapping(ControllerConstants.UPDATE_STATUS_DISH_PATH)
    @PreAuthorize(ControllerConstants.ROLE_PROPIETARIO)
    public ResponseEntity<UpdateDishStatusResponse> updateStatusDish(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @PathVariable Long id,
            @RequestParam Boolean newStatus
    ) {
        String token = authorizationHeader.replace(ControllerConstants.BEARER_PREFIX, "");
        UpdateDishStatusResponse response = dishService.updateStatusDish(id, newStatus, token);
        return ResponseEntity.ok(response);
    }

    @ListDishesDocs
    @GetMapping(ControllerConstants.LIST_DISHES_PATH)
    public ResponseEntity<PagedDishResponse> getDishesByCriteria(
            @RequestParam(required = false) Long restaurantId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = ControllerConstants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = ControllerConstants.DEFAULT_SIZE) int size
    ) {
        ListDishRequest request = new ListDishRequest(restaurantId, categoryId, page, size);
        PagedDishResponse response = dishService.listDishes(request);
        return ResponseEntity.ok(response);
    }
}
