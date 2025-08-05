package com.plazoleta.plazoleta.plazoleta.application.services.impl;

import com.plazoleta.plazoleta.commons.configurations.utils.Constants;
import com.plazoleta.plazoleta.plazoleta.application.dto.request.CreateEmployeeRestaurantRequest;
import com.plazoleta.plazoleta.plazoleta.application.dto.response.CreateEmployeeRestaurantResponse;
import com.plazoleta.plazoleta.plazoleta.application.services.EmployeeRestaurantService;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.EmployeeRestaurantServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.RoleValidatorPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmployeeRestaurantServiceImpl implements EmployeeRestaurantService {

    private final EmployeeRestaurantServicePort employeeRestaurantServicePort;
    private final RoleValidatorPort roleValidatorPort;

    @Override
    public void assignEmployeeToRestaurant(CreateEmployeeRestaurantRequest request, String token) {
        Long id = roleValidatorPort.extractUserId(token);
        String role = roleValidatorPort.extractRole(token);
        employeeRestaurantServicePort.assignEmployeeToRestaurant(request.employeeId(), request.restaurantId(), id, role);
    }

}
