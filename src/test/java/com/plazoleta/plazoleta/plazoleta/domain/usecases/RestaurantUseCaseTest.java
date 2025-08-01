package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.DuplicateNitException;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.ForbiddenException;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.PageNumberNegativeException;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.PageSizeInvalidException;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.UserValidationPort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;
import com.plazoleta.plazoleta.plazoleta.domain.utils.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    private final String ADMIN_ROLE = "ADMINISTRADOR";
    private final String NON_ADMIN_ROLE = "PROPIETARIO";

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
    void createRestaurant_withValidDataAndAdminRole_shouldSaveRestaurant() {
        UserModel ownerUser = new UserModel(1L, "PROPIETARIO");
        when(restaurantPersistencePort.getRestaurantByNit(anyString())).thenReturn(null);
        when(userValidationPort.getUserById(1L)).thenReturn(Optional.of(ownerUser));

        restaurantUseCase.createRestaurant(restaurantModel, ADMIN_ROLE);

        verify(restaurantPersistencePort, times(1)).saveRestaurant(any(RestaurantModel.class));
    }

    @Test
    void createRestaurant_whenHelperThrowsException_shouldNotSaveRestaurant() {

        when(restaurantPersistencePort.getRestaurantByNit("123456789")).thenReturn(new RestaurantModel());

        assertThrows(DuplicateNitException.class, () -> restaurantUseCase.createRestaurant(restaurantModel, ADMIN_ROLE));

        verify(restaurantPersistencePort, never()).saveRestaurant(any());
    }

    @Test
    void createRestaurant_withNonAdminRole_shouldThrowForbiddenException() {

        assertThrows(ForbiddenException.class, () -> restaurantUseCase.createRestaurant(restaurantModel, NON_ADMIN_ROLE));

        verify(restaurantPersistencePort, never()).saveRestaurant(any());
        verify(userValidationPort, never()).getUserById(any());
    }

    @Test
    void listRestaurants_withValidPagination_shouldReturnPageResult() {
        int page = 0;
        int size = 10;
        List<RestaurantModel> restaurants = List.of(restaurantModel, new RestaurantModel());
        PageResult<RestaurantModel> mockPageResult = new PageResult<>(
                restaurants,
                2L,
                1,
                0,
                10,
                true,
                false
        );

        when(restaurantPersistencePort.listRestaurantsOrderedByName(page, size))
                .thenReturn(mockPageResult);

        PageResult<RestaurantModel> result = restaurantUseCase.listRestaurants(page, size);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals(restaurants.size(), result.getContent().size());
        verify(restaurantPersistencePort, times(1)).listRestaurantsOrderedByName(page, size);
    }

    @Test
    void listRestaurants_withInvalidPageNumber_shouldThrowPageNumberNegativeException() {
        int invalidPage = -1;
        int validSize = 10;

        assertThrows(PageNumberNegativeException.class, () -> restaurantUseCase.listRestaurants(invalidPage, validSize));
        verify(restaurantPersistencePort, never()).listRestaurantsOrderedByName(anyInt(), anyInt());
    }

    @Test
    void listRestaurants_withInvalidPageSize_shouldThrowPageSizeInvalidException() {
        int validPage = 0;
        int invalidSize = 0;

        assertThrows(PageSizeInvalidException.class, () -> restaurantUseCase.listRestaurants(validPage, invalidSize));
        verify(restaurantPersistencePort, never()).listRestaurantsOrderedByName(anyInt(), anyInt());
    }
}