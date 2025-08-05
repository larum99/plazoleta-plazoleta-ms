package com.plazoleta.plazoleta.plazoleta.infrastructure.endpoints.rest;

import com.plazoleta.plazoleta.commons.configurations.swagger.docs.EmployeeRestaurantControllerDocs.*;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateEmployeeRestaurantRequest;
import com.plazoleta.plazoleta.plazoleta.application.services.EmployeeRestaurantService;

import com.plazoleta.plazoleta.plazoleta.infrastructure.utils.constants.ControllerConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControllerConstants.BASE_URL)
@RequiredArgsConstructor
public class EmployeeRestaurantController {

    private final EmployeeRestaurantService employeeRestaurantService;

    @CreateEmployeeRestaurantDocs
    @PostMapping(ControllerConstants.SAVE_PATH_EMPLOYEE_RESTAURANT)
    @PreAuthorize(ControllerConstants.ROLE_PROPIETARIO)
    public ResponseEntity<Void> associateEmployeeToRestaurant(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader,
            @RequestBody CreateEmployeeRestaurantRequest request) {

        String token = authorizationHeader.replace(ControllerConstants.BEARER_PREFIX, "");
        employeeRestaurantService.assignEmployeeToRestaurant(request, token);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
