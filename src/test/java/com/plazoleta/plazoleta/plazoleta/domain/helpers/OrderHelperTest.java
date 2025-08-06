package com.plazoleta.plazoleta.plazoleta.domain.helpers;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderDishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.EmployeeRestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.OrderPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;

import com.plazoleta.plazoleta.plazoleta.domain.utils.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderHelperTest {

    @Mock private OrderPersistencePort orderPersistencePort;
    @Mock private DishPersistencePort dishPersistencePort;
    @Mock private RestaurantPersistencePort restaurantPersistencePort;
    @Mock private EmployeeRestaurantPersistencePort employeeRestaurantPersistencePort;

    @InjectMocks
    private OrderHelper orderHelper;

    private static final Long CLIENT_ID = 1L;
    private static final Long RESTAURANT_ID = 10L;
    private static final Long DISH_ID = 100L;
    private static final Long ANOTHER_RESTAURANT_ID = 11L;
    private static final Long EMPLOYEE_ID = 20L;

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
        when(orderPersistencePort.getActiveOrderByClientId(CLIENT_ID))
                .thenReturn(Optional.of(new OrderModel()));
        assertThrows(ClientHasActiveOrderException.class, () -> orderHelper.validateNoActiveOrder(CLIENT_ID));
    }

    @Test
    void validateRestaurantExistence_whenRestaurantExists_shouldNotThrowException() {
        when(restaurantPersistencePort.getRestaurantById(RESTAURANT_ID))
                .thenReturn(Optional.of(restaurantModel));
        assertDoesNotThrow(() -> orderHelper.validateRestaurantExistence(RESTAURANT_ID));
    }

    @Test
    void validateRestaurantExistence_whenRestaurantNotFound_shouldThrowRestaurantNotFoundException() {
        when(restaurantPersistencePort.getRestaurantById(RESTAURANT_ID)).thenReturn(Optional.empty());
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

        assertDoesNotThrow(() -> orderHelper.validateDishesExistAndBelongToSameRestaurant(
                List.of(orderDish), RESTAURANT_ID));
    }

    @Test
    void validateDishesExistAndBelongToSameRestaurant_withDishNotFound_shouldThrowDishNotFoundException() {
        OrderDishModel orderDish = new OrderDishModel();
        orderDish.setDish(new DishModel() {{ setId(DISH_ID); }});
        when(dishPersistencePort.getDishById(DISH_ID)).thenReturn(Optional.empty());

        assertThrows(DishNotFoundException.class, () ->
                orderHelper.validateDishesExistAndBelongToSameRestaurant(List.of(orderDish), RESTAURANT_ID));
    }

    @Test
    void validateDishesExistAndBelongToSameRestaurant_withDishFromAnotherRestaurant_shouldThrowDishDoesNotBelongToRestaurantException() {
        RestaurantModel anotherRestaurant = new RestaurantModel();
        anotherRestaurant.setId(ANOTHER_RESTAURANT_ID);

        DishModel wrongDish = new DishModel();
        wrongDish.setId(DISH_ID);
        wrongDish.setRestaurant(anotherRestaurant);

        OrderDishModel orderDish = new OrderDishModel();
        orderDish.setDish(new DishModel() {{ setId(DISH_ID); }});
        when(dishPersistencePort.getDishById(DISH_ID)).thenReturn(Optional.of(wrongDish));

        assertThrows(DishDoesNotBelongToRestaurantException.class, () ->
                orderHelper.validateDishesExistAndBelongToSameRestaurant(List.of(orderDish), RESTAURANT_ID));
    }

    @Test
    void validateDishesExistAndBelongToSameRestaurant_withNullList_shouldThrowMissingFieldException() {
        assertThrows(MissingFieldException.class, () ->
                orderHelper.validateDishesExistAndBelongToSameRestaurant(null, RESTAURANT_ID));
    }

    @Test
    void validateDishesExistAndBelongToSameRestaurant_withEmptyList_shouldThrowMissingFieldException() {
        assertThrows(MissingFieldException.class, () ->
                orderHelper.validateDishesExistAndBelongToSameRestaurant(Collections.emptyList(), RESTAURANT_ID));
    }

    @Test
    void getRestaurantIdByEmployeeId_withValidAssociation_shouldReturnRestaurantId() {
        when(employeeRestaurantPersistencePort.getRestaurantIdByEmployeeId(EMPLOYEE_ID))
                .thenReturn(Optional.of(RESTAURANT_ID));

        Long result = orderHelper.getRestaurantIdByEmployeeId(EMPLOYEE_ID);
        assertEquals(RESTAURANT_ID, result);
    }

    @Test
    void getRestaurantIdByEmployeeId_withNoAssociation_shouldThrowForbiddenException() {
        when(employeeRestaurantPersistencePort.getRestaurantIdByEmployeeId(EMPLOYEE_ID))
                .thenReturn(Optional.empty());

        assertThrows(ForbiddenException.class, () -> orderHelper.getRestaurantIdByEmployeeId(EMPLOYEE_ID));
    }

    @ParameterizedTest
    @ValueSource(strings = {"EMPLEADO", "empleado"})
    void validateEmployeeRole_withValidRole_shouldNotThrow(String role) {
        assertDoesNotThrow(() -> orderHelper.validateEmployeeRole(role));
    }

    @ParameterizedTest
    @ValueSource(strings = {"CLIENTE", "ADMINISTRADOR", "PROPIETARIO", "INVALIDO"})
    void validateEmployeeRole_withInvalidRole_shouldThrowUnauthorizedUserException(String role) {
        assertThrows(UnauthorizedUserException.class, () -> orderHelper.validateEmployeeRole(role));
    }

    @Test
    void validateEmployeeCanAssignOrder_withMatchingRestaurant_shouldNotThrow() {
        OrderModel order = new OrderModel();
        RestaurantModel restaurant = new RestaurantModel();
        restaurant.setId(RESTAURANT_ID);
        order.setRestaurant(restaurant);

        when(employeeRestaurantPersistencePort.getRestaurantIdByEmployeeId(EMPLOYEE_ID))
                .thenReturn(Optional.of(RESTAURANT_ID));

        assertDoesNotThrow(() -> orderHelper.validateEmployeeCanAssignOrder(order, EMPLOYEE_ID));
    }

    @Test
    void validateEmployeeCanAssignOrder_withDifferentRestaurant_shouldThrowInvalidRestaurantAssignmentException() {
        OrderModel order = new OrderModel();
        RestaurantModel restaurant = new RestaurantModel();
        restaurant.setId(RESTAURANT_ID);
        order.setRestaurant(restaurant);

        when(employeeRestaurantPersistencePort.getRestaurantIdByEmployeeId(EMPLOYEE_ID))
                .thenReturn(Optional.of(ANOTHER_RESTAURANT_ID));

        assertThrows(InvalidRestaurantAssignmentException.class,
                () -> orderHelper.validateEmployeeCanAssignOrder(order, EMPLOYEE_ID));
    }

    @Test
    void validateOrderNotAssigned_whenOrderAlreadyAssigned_shouldThrowOrderAlreadyAssignedException() {
        OrderModel order = new OrderModel();
        order.setAssignedEmployeeId(EMPLOYEE_ID);

        assertThrows(OrderAlreadyAssignedException.class, () -> orderHelper.validateOrderNotAssigned(order));
    }

    @Test
    void validateOrderNotAssigned_whenOrderIsNotAssigned_shouldNotThrow() {
        OrderModel order = new OrderModel();
        order.setAssignedEmployeeId(null);

        assertDoesNotThrow(() -> orderHelper.validateOrderNotAssigned(order));
    }

    @Test
    void validateOrderIsPending_whenStatusIsNotPending_shouldThrowInvalidOrderStatusException() {
        OrderModel order = new OrderModel();
        order.setStatus(OrderStatus.EN_PREPARACION);

        assertThrows(InvalidOrderStatusException.class, () -> orderHelper.validateOrderIsPending(order));
    }

    @Test
    void validateOrderIsPending_whenStatusIsPending_shouldNotThrow() {
        OrderModel order = new OrderModel();
        order.setStatus(OrderStatus.PENDIENTE);

        assertDoesNotThrow(() -> orderHelper.validateOrderIsPending(order));
    }

    @Test
    void validateNewStatusIsInPreparation_whenStatusIsInvalid_shouldThrowInvalidOrderStatusException() {
        String invalidStatus = "ENTREGADO";

        assertThrows(InvalidOrderStatusException.class, () -> orderHelper.validateNewStatusIsInPreparation(invalidStatus));
    }

    @Test
    void validateNewStatusIsInPreparation_withValidStatus_shouldNotThrow() {
        String validStatus = "EN_PREPARACION";

        assertDoesNotThrow(() -> orderHelper.validateNewStatusIsInPreparation(validStatus));
    }
}
