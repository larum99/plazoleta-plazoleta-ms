package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.DishCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import com.plazoleta.plazoleta.plazoleta.domain.helpers.DishHelper;
import com.plazoleta.plazoleta.plazoleta.domain.model.CategoryModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.CategoryPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {

    @Mock
    private DishPersistencePort dishPersistencePort;
    @Mock
    private RestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private CategoryPersistencePort categoryPersistencePort;
    @Mock
    private DishHelper dishHelper;

    @InjectMocks
    private DishUseCase dishUseCase;

    private static final Long MOCK_OWNER_ID = 1L;
    private static final Long MOCK_RESTAURANT_ID = 1L;
    private static final Long MOCK_CATEGORY_ID = 1L;
    private static final Long MOCK_DISH_ID = 10L;
    private static final String OWNER_ROLE = "PROPIETARIO";
    private static final String NON_OWNER_ROLE = "CLIENTE";


    private DishModel dishModel;
    private RestaurantModel restaurantModel;

    @BeforeEach
    void setUp() {

        restaurantModel = new RestaurantModel();
        restaurantModel.setId(MOCK_RESTAURANT_ID);
        restaurantModel.setOwnerId(MOCK_OWNER_ID);

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(MOCK_CATEGORY_ID);

        dishModel = new DishModel();
        dishModel.setId(MOCK_DISH_ID);
        dishModel.setName("Plato de Prueba");
        dishModel.setPrice(new BigDecimal("25000.00"));
        dishModel.setDescription("Una descripción");
        dishModel.setImageUrl("https://example.com/image.png");
        dishModel.setRestaurant(restaurantModel);
        dishModel.setCategory(categoryModel);
        dishModel.setActive(true);

        dishUseCase = new DishUseCase(
                dishPersistencePort,
                restaurantPersistencePort,
                categoryPersistencePort
        );
    }

    @Test
    void createDish_withValidData_shouldSaveDish() {
        when(restaurantPersistencePort.getRestaurantById(MOCK_RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));
        when(categoryPersistencePort.getCategoryById(MOCK_CATEGORY_ID)).thenReturn(Optional.of(dishModel.getCategory()));

        dishUseCase.createDish(dishModel, OWNER_ROLE, MOCK_OWNER_ID);

        ArgumentCaptor<DishModel> dishCaptor = ArgumentCaptor.forClass(DishModel.class);
        verify(dishPersistencePort).saveDish(dishCaptor.capture());
        DishModel savedDish = dishCaptor.getValue();

        assertTrue(savedDish.getActive());
        assertEquals("Plato de Prueba", savedDish.getName());
    }

    @Test
    void createDish_withNonOwnerRole_shouldThrowUnauthorizedUserException() {
        assertThrows(UnauthorizedUserException.class, () -> dishUseCase.createDish(dishModel, NON_OWNER_ROLE, MOCK_OWNER_ID));

        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void updateDish_withValidData_shouldSucceed() {
        BigDecimal newPrice = new BigDecimal("30000.00");
        String newDescription = "Nueva descripción";

        when(dishPersistencePort.getDishById(MOCK_DISH_ID)).thenReturn(Optional.of(dishModel));
        when(restaurantPersistencePort.getRestaurantById(MOCK_RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));

        dishUseCase.updateDish(MOCK_DISH_ID, newDescription, newPrice, MOCK_OWNER_ID);

        ArgumentCaptor<DishModel> dishCaptor = ArgumentCaptor.forClass(DishModel.class);
        verify(dishPersistencePort).updateDish(dishCaptor.capture());
        DishModel updatedDish = dishCaptor.getValue();

        assertEquals(newPrice, updatedDish.getPrice());
        assertEquals(newDescription, updatedDish.getDescription());
    }

    @Test
    void updateDish_withMismatchedOwner_shouldThrowUnauthorizedUserException() {
        Long wrongOwnerId = 999L;
        when(dishPersistencePort.getDishById(MOCK_DISH_ID)).thenReturn(Optional.of(dishModel));
        when(restaurantPersistencePort.getRestaurantById(MOCK_RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));

        assertThrows(UnauthorizedUserException.class, () -> dishUseCase.updateDish(MOCK_DISH_ID, "desc", new BigDecimal("100"), wrongOwnerId));

        verify(dishPersistencePort, never()).updateDish(any());
    }

    @Test
    void updateDish_whenDishNotFound_shouldThrowDishNotFoundException() {
        when(dishPersistencePort.getDishById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DishNotFoundException.class, () -> dishUseCase.updateDish(99L, "desc", new BigDecimal("100"), MOCK_OWNER_ID));

        verify(dishPersistencePort, never()).updateDish(any());
    }

    @Test
    void changeDishStatus_withValidData_shouldUpdateStatus() {
        when(dishPersistencePort.getDishById(MOCK_DISH_ID)).thenReturn(Optional.of(dishModel));
        when(restaurantPersistencePort.getRestaurantById(MOCK_RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));

        dishUseCase.changeDishStatus(MOCK_DISH_ID, MOCK_OWNER_ID, false);

        ArgumentCaptor<DishModel> dishCaptor = ArgumentCaptor.forClass(DishModel.class);
        verify(dishPersistencePort).updateDish(dishCaptor.capture());
        DishModel updatedDish = dishCaptor.getValue();

        assertFalse(updatedDish.getActive());
        assertEquals(MOCK_DISH_ID, updatedDish.getId());
    }

    @Test
    void changeDishStatus_whenDishNotFound_shouldThrowDishNotFoundException() {
        when(dishPersistencePort.getDishById(MOCK_DISH_ID)).thenReturn(Optional.empty());

        assertThrows(DishNotFoundException.class, () -> dishUseCase.changeDishStatus(MOCK_DISH_ID, MOCK_OWNER_ID, false));

        verify(dishPersistencePort, never()).updateDish(any(DishModel.class));
    }

    @Test
    void changeDishStatus_withMismatchedOwner_shouldThrowUnauthorizedUserException() {
        Long wrongOwnerId = 999L;
        when(dishPersistencePort.getDishById(MOCK_DISH_ID)).thenReturn(Optional.of(dishModel));
        when(restaurantPersistencePort.getRestaurantById(MOCK_RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));

        assertThrows(UnauthorizedUserException.class, () -> dishUseCase.changeDishStatus(MOCK_DISH_ID, wrongOwnerId, false));

        verify(dishPersistencePort, never()).updateDish(any(DishModel.class));
    }

    @Test
    void getMenuByCriteria_withValidCriteria_shouldReturnPageResult() {
        DishCriteria criteria = new DishCriteria(MOCK_RESTAURANT_ID, MOCK_CATEGORY_ID, 0, 10);
        PageResult<DishModel> expectedPageResult = new PageResult<>(List.of(dishModel), 1L, 1, 0, 10, true, true);
        when(dishPersistencePort.getMenuByCriteria(criteria)).thenReturn(expectedPageResult);

        PageResult<DishModel> result = dishUseCase.getMenuByCriteria(criteria);

        verify(dishPersistencePort).getMenuByCriteria(criteria);
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(MOCK_DISH_ID, result.getContent().get(0).getId());
    }

    @Test
    void getMenuByCriteria_withInvalidCriteria_shouldThrowException() {
        DishCriteria invalidCriteria = new DishCriteria(null, MOCK_CATEGORY_ID, 0, 10);

        assertThrows(MissingFieldException.class, () -> dishUseCase.getMenuByCriteria(invalidCriteria));

        verify(dishPersistencePort, never()).getMenuByCriteria(any());
    }

    @Test
    void getMenuByCriteria_whenPortReturnsEmptyResult_shouldReturnEmptyPageResult() {
        DishCriteria criteria = new DishCriteria(MOCK_RESTAURANT_ID, MOCK_CATEGORY_ID, 0, 10);
        PageResult<DishModel> emptyPageResult = new PageResult<>(Collections.emptyList(), 0L, 0, 0, 10, true, true);
        when(dishPersistencePort.getMenuByCriteria(criteria)).thenReturn(emptyPageResult);

        PageResult<DishModel> result = dishUseCase.getMenuByCriteria(criteria);

        verify(dishPersistencePort).getMenuByCriteria(criteria);
        assertNotNull(result);
        assertTrue(result.getContent().isEmpty());
    }
}