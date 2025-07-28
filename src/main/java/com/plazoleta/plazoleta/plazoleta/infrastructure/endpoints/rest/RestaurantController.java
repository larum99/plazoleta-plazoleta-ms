package com.plazoleta.plazoleta.plazoleta.infrastructure.endpoints.rest;

import com.plazoleta.plazoleta.commons.configurations.swagger.docs.RestaurantControllerDocs.CreateRestaurantDocs;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.SaveRestaurantRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.SaveRestaurantResponse;
import com.plazoleta.plazoleta.plazoleta.application.services.RestaurantService;
import com.plazoleta.plazoleta.plazoleta.infrastructure.utils.constants.ControllerConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstants.BASE_URL)
@RequiredArgsConstructor
@Tag(name = ControllerConstants.TAG, description = ControllerConstants.TAG_DESCRIPTION)
public class RestaurantController {

    private final RestaurantService restaurantService;

    @CreateRestaurantDocs
    @PostMapping(ControllerConstants.SAVE_PATH_RESTAURANT)
    public ResponseEntity<SaveRestaurantResponse> saveRestaurant(@RequestBody SaveRestaurantRequest request) {
        SaveRestaurantResponse response = restaurantService.save(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
