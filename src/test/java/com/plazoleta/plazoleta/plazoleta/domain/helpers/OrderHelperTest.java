package com.plazoleta.plazoleta.plazoleta.domain.helpers;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderDishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.OrderPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderHelperTest {

    @Mock
    private OrderPersistencePort orderPersistencePort;
    @Mock
    private DishPersistencePort dishPersistencePort;
    @Mock
    private RestaurantPersistencePort restaurantPersistencePort;

    @InjectMocks
    private OrderHelper orderHelper;

    private static final Long CLIENT_ID = 1L;
    private static final Long RESTAURANT_ID = 10L;
    private static final Long DISH_ID = 100L;
    private static final Long ANOTHER_RESTAURANT_ID = 11L;

    private RestaurantModel restaurantModel;
    private DishModel dishModel;

    @BeforeEach
    void setUp() {
        restaurantModel = new RestaurantModel();
        restaurantModel.setId(RESTAURANT_ID);

        dishModel = new DishModel();
        dishModel.setId(DISH_ID);
        dishModel.setRestaurant(restaurantModel);
    }

    @Test
    void validateRole_withClientRole_shouldNotThrowException() {
        assertDoesNotThrow(() -> orderHelper.validateRole(DomainConstants.ROLE_CLIENT));
    }

    @ParameterizedTest
    @ValueSource(strings = {"PROPIETARIO", "ADMINISTRADOR", "EMPLEADO", "invalid"})
    void validateRole_withNonClientRole_shouldThrowUnauthorizedUserException(String role) {
        assertThrows(UnauthorizedUserException.class, () -> orderHelper.validateRole(role));
    }

    @Test
    void validateNoActiveOrder_whenNoActiveOrderExists_shouldNotThrowException() {
        when(orderPersistencePort.getActiveOrderByClientId(CLIENT_ID)).thenReturn(Optional.empty());
        assertDoesNotThrow(() -> orderHelper.validateNoActiveOrder(CLIENT_ID));
    }

    @Test
    void validateNoActiveOrder_whenActiveOrderExists_shouldThrowClientHasActiveOrderException() {
        OrderModel activeOrder = new OrderModel();
        when(orderPersistencePort.getActiveOrderByClientId(CLIENT_ID)).thenReturn(Optional.of(activeOrder));
        assertThrows(ClientHasActiveOrderException.class, () -> orderHelper.validateNoActiveOrder(CLIENT_ID));
    }

    @Test
    void validateRestaurantExistence_whenRestaurantExists_shouldNotThrowException() {
        when(restaurantPersistencePort.getRestaurantById(RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));
        assertDoesNotThrow(() -> orderHelper.validateRestaurantExistence(RESTAURANT_ID));
    }

    @Test
    void validateRestaurantExistence_whenRestaurantNotFound_shouldThrowRestaurantNotFoundException() {
        when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class, () -> orderHelper.validateRestaurantExistence(RESTAURANT_ID));
    }

    @Test
    void validateRestaurantExistence_withNullRestaurantId_shouldThrowMissingFieldException() {
        assertThrows(MissingFieldException.class, () -> orderHelper.validateRestaurantExistence(null));
    }

    @Test
    void validateDishesExistAndBelongToSameRestaurant_withValidDishes_shouldNotThrowException() {
        OrderDishModel orderDish = new OrderDishModel();
        orderDish.setDish(new DishModel() {{ setId(DISH_ID); }});

        when(dishPersistencePort.getDishById(DISH_ID)).thenReturn(Optional.of(dishModel));

        assertDoesNotThrow(() -> orderHelper.validateDishesExistAndBelongToSameRestaurant(List.of(orderDish), RESTAURANT_ID));
    }

    @Test
    void validateDishesExistAndBelongToSameRestaurant_withDishNotFound_shouldThrowDishNotFoundException() {
        OrderDishModel orderDish = new OrderDishModel();
        orderDish.setDish(new DishModel() {{ setId(DISH_ID); }});

        when(dishPersistencePort.getDishById(DISH_ID)).thenReturn(Optional.empty());

        assertThrows(DishNotFoundException.class, () -> orderHelper.validateDishesExistAndBelongToSameRestaurant(List.of(orderDish), RESTAURANT_ID));
    }

    @Test
    void validateDishesExistAndBelongToSameRestaurant_withDishNotBelongingToRestaurant_shouldThrowDishDoesNotBelongToRestaurantException() {
        RestaurantModel anotherRestaurant = new RestaurantModel();
        anotherRestaurant.setId(ANOTHER_RESTAURANT_ID);

        DishModel dishFromAnotherRestaurant = new DishModel();
        dishFromAnotherRestaurant.setId(DISH_ID);
        dishFromAnotherRestaurant.setRestaurant(anotherRestaurant);

        OrderDishModel orderDish = new OrderDishModel();
        orderDish.setDish(new DishModel() {{ setId(DISH_ID); }});

        when(dishPersistencePort.getDishById(DISH_ID)).thenReturn(Optional.of(dishFromAnotherRestaurant));

        assertThrows(DishDoesNotBelongToRestaurantException.class, () -> orderHelper.validateDishesExistAndBelongToSameRestaurant(List.of(orderDish), RESTAURANT_ID));
    }

    @Test
    void validateDishesExistAndBelongToSameRestaurant_withNullDishes_shouldThrowMissingFieldException() {
        assertThrows(MissingFieldException.class, () -> orderHelper.validateDishesExistAndBelongToSameRestaurant(null, RESTAURANT_ID));
    }

    @Test
    void validateDishesExistAndBelongToSameRestaurant_withEmptyDishes_shouldThrowMissingFieldException() {
        assertThrows(MissingFieldException.class, () -> orderHelper.validateDishesExistAndBelongToSameRestaurant(Collections.emptyList(), RESTAURANT_ID));
    }
}