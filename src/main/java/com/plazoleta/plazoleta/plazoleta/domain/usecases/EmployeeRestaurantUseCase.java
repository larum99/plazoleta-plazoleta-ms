package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.EmployeeAlreadyAssignedException;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.InvalidEmployeeException;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.UserNotFoundException;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.EmployeeRestaurantServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.EmployeeRestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.UserValidationPort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;
import com.plazoleta.plazoleta.plazoleta.domain.utils.UserModel;

public class EmployeeRestaurantUseCase implements EmployeeRestaurantServicePort {

    private final EmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;
    private final UserValidationPort userValidationPort;

    public EmployeeRestaurantUseCase(
            EmployeeRestaurantPersistencePort employeeRestaurantPersistencePort,
            UserValidationPort userValidationPort
    ) {
        this.employeeRestaurantPersistencePort = employeeRestaurantPersistencePort;
        this.userValidationPort = userValidationPort;
    }

    @Override
    public void assignEmployeeToRestaurant(Long employeeId, Long restaurantId, Long id, String role) {
        validateUserIsEmployee(employeeId);
        validateNoExistingAssociation(employeeId, restaurantId);
        employeeRestaurantPersistencePort.saveAssociation(employeeId, restaurantId);
    }

    private void validateUserIsEmployee(Long userId) {
        UserModel user = userValidationPort.getUserById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (!DomainConstants.ROLE_EMPLOYEE.equalsIgnoreCase(user.getRole())) {
            throw new InvalidEmployeeException();
        }
    }

    private void validateNoExistingAssociation(Long employeeId, Long restaurantId) {
        boolean alreadyExists = employeeRestaurantPersistencePort.existsAssociation(employeeId, restaurantId);
        if (alreadyExists) {
            throw new EmployeeAlreadyAssignedException();
        }
    }
}
