package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.utils.UserModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.UserValidationPort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {

    @Mock
    private RestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private UserValidationPort userValidationPort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    private RestaurantModel restaurantModel;

    @BeforeEach
    void setUp() {
        restaurantModel = new RestaurantModel();
        restaurantModel.setName("Restaurante Delicias");
        restaurantModel.setNit("123456789");
        restaurantModel.setAddress("Calle Falsa 123");
        restaurantModel.setPhone("+573001234567");
        restaurantModel.setLogoUrl("https://example.com/logo.png");
        restaurantModel.setOwnerId(1L);
    }

    @Test
    void createRestaurant_withValidData_shouldSaveRestaurant() {
        // Arrange
        UserModel ownerUser = mock(UserModel.class);
        when(ownerUser.getRole()).thenReturn(DomainConstants.OWNER);
        when(restaurantPersistencePort.getRestaurantByNit(anyString())).thenReturn(null);
        when(userValidationPort.getUserById(1L)).thenReturn(Optional.of(ownerUser));
        // Act
        restaurantUseCase.createRestaurant(restaurantModel);
        // Assert
        verify(restaurantPersistencePort).saveRestaurant(any(RestaurantModel.class));
    }

    @Test
    void createRestaurant_withMissingName_shouldThrowMissingFieldException() {
        restaurantModel.setName(null);
        assertThrows(MissingFieldException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void createRestaurant_withInvalidNitFormat_shouldThrowInvalidNitException() {
        restaurantModel.setNit("12345ABC");
        assertThrows(InvalidNitException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void createRestaurant_whenNitAlreadyExists_shouldThrowDuplicateNitException() {
        when(restaurantPersistencePort.getRestaurantByNit("123456789")).thenReturn(new RestaurantModel());
        assertThrows(DuplicateNitException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void createRestaurant_withInvalidPhoneFormat_shouldThrowInvalidPhoneException() {
        when(restaurantPersistencePort.getRestaurantByNit(anyString())).thenReturn(null);
        restaurantModel.setPhone("+ABC");
        assertThrows(InvalidPhoneException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).getUserById(anyLong());
    }

    @Test
    void createRestaurant_withInvalidName_shouldThrowInvalidRestaurantNameException() {
        restaurantModel.setName("123456");
        assertThrows(InvalidRestaurantNameException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }



    @Test
    void createRestaurant_withInvalidLogoUrl_shouldThrowInvalidLogoUrlException() {
        restaurantModel.setLogoUrl("not-a-valid-url");
        assertThrows(InvalidLogoUrlException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void createRestaurant_whenOwnerNotFound_shouldThrowUserNotFoundException() {
        when(restaurantPersistencePort.getRestaurantByNit(anyString())).thenReturn(null);
        when(userValidationPort.getUserById(1L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void createRestaurant_whenUserIsNotOwner_shouldThrowInvalidOwnerException() {
        UserModel clientUser = mock(UserModel.class);
        when(clientUser.getRole()).thenReturn("CLIENTE");

        when(restaurantPersistencePort.getRestaurantByNit(anyString())).thenReturn(null);
        when(userValidationPort.getUserById(1L)).thenReturn(Optional.of(clientUser));

        assertThrows(InvalidOwnerException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void createRestaurant_withMissingOwnerId_shouldThrowMissingFieldException() {
        restaurantModel.setOwnerId(null);
        assertThrows(MissingFieldException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));
        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).getUserById(anyLong());
    }
}