package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.OrderListCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import com.plazoleta.plazoleta.plazoleta.domain.helpers.OrderHelper;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderDishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.OrderModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.OrderPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.OrderStatus;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {

    @Mock
    private OrderPersistencePort orderPersistencePort;
    @Mock
    private DishPersistencePort dishPersistencePort;
    @Mock
    private RestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private OrderHelper orderHelper;

    @InjectMocks
    private OrderUseCase orderUseCase;

    private static final Long CLIENT_ID = 1L;
    private static final Long EMPLOYEE_ID = 2L;
    private static final Long RESTAURANT_ID = 10L;
    private static final Long DISH_ID = 100L;
    private static final String CLIENT_ROLE = "CLIENTE";
    private static final String OWNER_ROLE = "PROPIETARIO";

    private OrderModel validOrder;
    private RestaurantModel restaurantModel;
    private DishModel dishModel;
    private OrderDishModel orderDishModel;

    @BeforeEach
    void setUp() {
        restaurantModel = new RestaurantModel();
        restaurantModel.setId(RESTAURANT_ID);

        dishModel = new DishModel();
        dishModel.setId(DISH_ID);
        dishModel.setRestaurant(restaurantModel);

        orderDishModel = new OrderDishModel();
        orderDishModel.setDish(dishModel);
        orderDishModel.setQuantity(2);

        validOrder = new OrderModel();
        validOrder.setRestaurant(restaurantModel);
        validOrder.setDishes(List.of(orderDishModel));

        orderUseCase = new OrderUseCase(orderPersistencePort, dishPersistencePort, restaurantPersistencePort, null);
        ReflectionTestUtils.setField(orderUseCase, "orderHelper", orderHelper);
    }

    @Test
    void createOrder_withValidDataAndClientRole_shouldSaveOrder() {
        doNothing().when(orderHelper).validateRole(anyString());
        doNothing().when(orderHelper).validateNoActiveOrder(anyLong());
        doNothing().when(orderHelper).validateRestaurantExistence(anyLong());
        doNothing().when(orderHelper).validateDishesExistAndBelongToSameRestaurant(any(), anyLong());

        orderUseCase.createOrder(validOrder, CLIENT_ID, CLIENT_ROLE);

        ArgumentCaptor<OrderModel> orderCaptor = ArgumentCaptor.forClass(OrderModel.class);
        verify(orderPersistencePort).saveOrder(orderCaptor.capture());

        OrderModel savedOrder = orderCaptor.getValue();
        assertEquals(CLIENT_ID, savedOrder.getClientId());
        assertEquals(OrderStatus.PENDIENTE, savedOrder.getStatus());
        assertNotNull(savedOrder.getDate());
    }

    @Test
    void createOrder_withNonClientRole_shouldThrowUnauthorizedUserException() {
        doThrow(UnauthorizedUserException.class).when(orderHelper).validateRole(anyString());

        assertThrows(UnauthorizedUserException.class, () -> orderUseCase.createOrder(validOrder, CLIENT_ID, OWNER_ROLE));

        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void createOrder_whenClientHasActiveOrder_shouldThrowClientHasActiveOrderException() {
        doNothing().when(orderHelper).validateRole(anyString());
        doThrow(ClientHasActiveOrderException.class).when(orderHelper).validateNoActiveOrder(anyLong());

        assertThrows(ClientHasActiveOrderException.class, () -> orderUseCase.createOrder(validOrder, CLIENT_ID, CLIENT_ROLE));

        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void createOrder_whenRestaurantNotFound_shouldThrowRestaurantNotFoundException() {
        doNothing().when(orderHelper).validateRole(anyString());
        doNothing().when(orderHelper).validateNoActiveOrder(anyLong());
        doThrow(RestaurantNotFoundException.class).when(orderHelper).validateRestaurantExistence(anyLong());

        assertThrows(RestaurantNotFoundException.class, () -> orderUseCase.createOrder(validOrder, CLIENT_ID, CLIENT_ROLE));

        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void createOrder_whenDishNotFound_shouldThrowDishNotFoundException() {
        doNothing().when(orderHelper).validateRole(anyString());
        doNothing().when(orderHelper).validateNoActiveOrder(anyLong());
        doNothing().when(orderHelper).validateRestaurantExistence(anyLong());
        doThrow(DishNotFoundException.class).when(orderHelper).validateDishesExistAndBelongToSameRestaurant(any(), anyLong());

        assertThrows(DishNotFoundException.class, () -> orderUseCase.createOrder(validOrder, CLIENT_ID, CLIENT_ROLE));

        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void createOrder_whenDishDoesNotBelongToRestaurant_shouldThrowDishDoesNotBelongToRestaurantException() {
        doNothing().when(orderHelper).validateRole(anyString());
        doNothing().when(orderHelper).validateNoActiveOrder(anyLong());
        doNothing().when(orderHelper).validateRestaurantExistence(anyLong());
        doThrow(DishDoesNotBelongToRestaurantException.class).when(orderHelper).validateDishesExistAndBelongToSameRestaurant(any(), anyLong());

        assertThrows(DishDoesNotBelongToRestaurantException.class, () -> orderUseCase.createOrder(validOrder, CLIENT_ID, CLIENT_ROLE));

        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void createOrder_whenOrderDishesListIsEmpty_shouldThrowMissingFieldException() {
        validOrder.setDishes(Collections.emptyList());

        doNothing().when(orderHelper).validateRole(anyString());
        doNothing().when(orderHelper).validateNoActiveOrder(anyLong());
        doNothing().when(orderHelper).validateRestaurantExistence(anyLong());
        doThrow(MissingFieldException.class).when(orderHelper).validateDishesExistAndBelongToSameRestaurant(any(), anyLong());

        assertThrows(MissingFieldException.class, () -> orderUseCase.createOrder(validOrder, CLIENT_ID, CLIENT_ROLE));

        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void listOrders_withValidCriteria_shouldReturnPageResult() {
        Long expectedRestaurantId = RESTAURANT_ID;
        String status = OrderStatus.PENDIENTE.name();
        int page = 0;
        int size = 10;

        OrderListCriteria inputCriteria = new OrderListCriteria(
                null,
                status,
                page,
                size
        );

        PageResult<OrderModel> expectedPageResult = new PageResult<>(
                List.of(validOrder), 1L, 1, page, size, true, true
        );

        when(orderHelper.getRestaurantIdByEmployeeId(EMPLOYEE_ID)).thenReturn(expectedRestaurantId);
        when(orderPersistencePort.getOrdersByCriteria(any(OrderListCriteria.class))).thenReturn(expectedPageResult);

        PageResult<OrderModel> result = orderUseCase.listOrders(inputCriteria, OWNER_ROLE, EMPLOYEE_ID);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(expectedRestaurantId, result.getContent().get(0).getRestaurant().getId());

        ArgumentCaptor<OrderListCriteria> criteriaCaptor = ArgumentCaptor.forClass(OrderListCriteria.class);
        verify(orderPersistencePort).getOrdersByCriteria(criteriaCaptor.capture());

        OrderListCriteria usedCriteria = criteriaCaptor.getValue();
        assertEquals(expectedRestaurantId, usedCriteria.getRestaurantId());
        assertEquals(status, usedCriteria.getStatus());
        assertEquals(page, usedCriteria.getPage());
        assertEquals(size, usedCriteria.getSize());
    }

    @Test
    void assignOrderAndChangeStatus_withValidData_shouldUpdateOrder() {
        Long orderId = 1L;
        String newStatus = "EN_PREPARACION";

        OrderModel order = new OrderModel();
        order.setId(orderId);
        order.setStatus(OrderStatus.PENDIENTE);

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(java.util.Optional.of(order));
        doNothing().when(orderHelper).validateEmployeeRole(OWNER_ROLE);
        doNothing().when(orderHelper).validateEmployeeCanAssignOrder(order, EMPLOYEE_ID);
        doNothing().when(orderHelper).validateOrderNotAssigned(order);
        doNothing().when(orderHelper).validateOrderIsPending(order);
        doNothing().when(orderHelper).validateNewStatusIsInPreparation(newStatus);

        orderUseCase.assignOrderAndChangeStatus(orderId, EMPLOYEE_ID, OWNER_ROLE, newStatus);

        assertEquals(OrderStatus.EN_PREPARACION, order.getStatus());
        assertEquals(EMPLOYEE_ID, order.getAssignedEmployeeId());

        verify(orderPersistencePort).saveOrder(order);
    }

    @Test
    void assignOrderAndChangeStatus_orderNotFound_shouldThrowException() {
        Long orderId = 999L;
        String newStatus = "EN_PREPARACION";

        doNothing().when(orderHelper).validateEmployeeRole(anyString());
        when(orderPersistencePort.getOrderById(orderId)).thenReturn(java.util.Optional.empty());

        assertThrows(OrderNotFoundException.class,
                () -> orderUseCase.assignOrderAndChangeStatus(orderId, EMPLOYEE_ID, OWNER_ROLE, newStatus));

        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void assignOrderAndChangeStatus_orderAlreadyAssigned_shouldThrowException() {
        Long orderId = 1L;
        String newStatus = "EN_PREPARACION";

        OrderModel order = new OrderModel();
        order.setId(orderId);
        order.setStatus(OrderStatus.PENDIENTE);

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(java.util.Optional.of(order));
        doNothing().when(orderHelper).validateEmployeeRole(anyString());
        doThrow(IllegalStateException.class).when(orderHelper).validateOrderNotAssigned(order);

        assertThrows(IllegalStateException.class,
                () -> orderUseCase.assignOrderAndChangeStatus(orderId, EMPLOYEE_ID, OWNER_ROLE, newStatus));

        verify(orderPersistencePort, never()).saveOrder(any());
    }

    @Test
    void assignOrderAndChangeStatus_invalidStatus_shouldThrowException() {
        Long orderId = 1L;
        String invalidStatus = "CANCELADO";

        OrderModel order = new OrderModel();
        order.setId(orderId);
        order.setStatus(OrderStatus.PENDIENTE);

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(java.util.Optional.of(order));
        doNothing().when(orderHelper).validateEmployeeRole(anyString());
        doNothing().when(orderHelper).validateOrderNotAssigned(order);
        doNothing().when(orderHelper).validateOrderIsPending(order);
        doThrow(IllegalArgumentException.class).when(orderHelper).validateNewStatusIsInPreparation(invalidStatus);

        assertThrows(IllegalArgumentException.class,
                () -> orderUseCase.assignOrderAndChangeStatus(orderId, EMPLOYEE_ID, OWNER_ROLE, invalidStatus));

        verify(orderPersistencePort, never()).saveOrder(any());
    }
}