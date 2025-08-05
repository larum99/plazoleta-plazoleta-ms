package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.EmployeeAlreadyAssignedException;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.InvalidEmployeeException;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.UserNotFoundException;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.EmployeeRestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.UserValidationPort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeRestaurantUseCaseTest {

    @Mock
    private EmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;

    @Mock
    private UserValidationPort userValidationPort;

    @InjectMocks
    private EmployeeRestaurantUseCase employeeRestaurantUseCase;

    private final Long validEmployeeId = 1L;
    private final Long restaurantId = 2L;
    private final Long requesterId = 3L;
    private final String requesterRole = "ADMINISTRADOR";

    private UserModel employeeUser;

    @BeforeEach
    void setUp() {
        employeeUser = new UserModel(validEmployeeId, "EMPLEADO");
    }

    @Test
    void assignEmployeeToRestaurant_withValidData_shouldSaveAssociation() {
        when(userValidationPort.getUserById(validEmployeeId)).thenReturn(Optional.of(employeeUser));
        when(employeeRestaurantPersistencePort.existsAssociation(validEmployeeId, restaurantId)).thenReturn(false);

        employeeRestaurantUseCase.assignEmployeeToRestaurant(validEmployeeId, restaurantId, requesterId, requesterRole);

        verify(employeeRestaurantPersistencePort, times(1)).saveAssociation(validEmployeeId, restaurantId);
    }

    @Test
    void assignEmployeeToRestaurant_whenUserNotFound_shouldThrowUserNotFoundException() {
        when(userValidationPort.getUserById(validEmployeeId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                employeeRestaurantUseCase.assignEmployeeToRestaurant(validEmployeeId, restaurantId, requesterId, requesterRole)
        );

        verify(employeeRestaurantPersistencePort, never()).saveAssociation(anyLong(), anyLong());
    }

    @Test
    void assignEmployeeToRestaurant_whenUserNotEmployee_shouldThrowInvalidEmployeeException() {
        UserModel invalidUser = new UserModel(validEmployeeId, "PROPIETARIO");
        when(userValidationPort.getUserById(validEmployeeId)).thenReturn(Optional.of(invalidUser));

        assertThrows(InvalidEmployeeException.class, () ->
                employeeRestaurantUseCase.assignEmployeeToRestaurant(validEmployeeId, restaurantId, requesterId, requesterRole)
        );

        verify(employeeRestaurantPersistencePort, never()).saveAssociation(anyLong(), anyLong());
    }

    @Test
    void assignEmployeeToRestaurant_whenAssociationAlreadyExists_shouldThrowEmployeeAlreadyAssignedException() {
        when(userValidationPort.getUserById(validEmployeeId)).thenReturn(Optional.of(employeeUser));
        when(employeeRestaurantPersistencePort.existsAssociation(validEmployeeId, restaurantId)).thenReturn(true);

        assertThrows(EmployeeAlreadyAssignedException.class, () ->
                employeeRestaurantUseCase.assignEmployeeToRestaurant(validEmployeeId, restaurantId, requesterId, requesterRole)
        );

        verify(employeeRestaurantPersistencePort, never()).saveAssociation(anyLong(), anyLong());
    }
}
