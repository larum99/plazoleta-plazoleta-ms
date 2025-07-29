package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.DuplicateNitException;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
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
import static org.mockito.ArgumentMatchers.any;
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
        UserModel ownerUser = new UserModel(1L, "PROPIETARIO");
        when(restaurantPersistencePort.getRestaurantByNit(anyString())).thenReturn(null);
        when(userValidationPort.getUserById(1L)).thenReturn(Optional.of(ownerUser));

        restaurantUseCase.createRestaurant(restaurantModel);

        verify(restaurantPersistencePort, times(1)).saveRestaurant(any(RestaurantModel.class));
    }

    @Test
    void createRestaurant_whenHelperThrowsException_shouldNotSaveRestaurant() {
        when(restaurantPersistencePort.getRestaurantByNit("123456789")).thenReturn(new RestaurantModel());

        assertThrows(DuplicateNitException.class, () -> restaurantUseCase.createRestaurant(restaurantModel));

        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }
}