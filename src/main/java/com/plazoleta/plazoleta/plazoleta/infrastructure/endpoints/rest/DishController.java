package com.plazoleta.plazoleta.plazoleta.infrastructure.endpoints.rest;

import com.plazoleta.plazoleta.commons.configurations.swagger.docs.DishControllerDocs.*;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.UpdateDishRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveDishResponse;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.UpdateDishResponse;
import com.plazoleta.plazoleta.plazoleta.application.services.DishService;
import com.plazoleta.plazoleta.plazoleta.infrastructure.utils.constants.ControllerConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstants.BASE_URL)
@RequiredArgsConstructor
@Tag(name = ControllerConstants.TAG_DISH, description = ControllerConstants.TAG_DISH_DESCRIPTION)
public class DishController {

    private final DishService dishService;

    @CreateDishDocs
    @PostMapping(ControllerConstants.SAVE_DISH_PATH)
    public ResponseEntity<SaveDishResponse> saveDish(@RequestBody SaveDishRequest request) {
        SaveDishResponse response = dishService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @UpdateDishDocs
    @PutMapping(ControllerConstants.UPDATE_DISH_PATH)
    public ResponseEntity<UpdateDishResponse> updateDish(
            @PathVariable Long id,
            @RequestBody UpdateDishRequest request
    ) {
        UpdateDishResponse response = dishService.updateDish(id, request);
        return ResponseEntity.ok(response);
    }
}
