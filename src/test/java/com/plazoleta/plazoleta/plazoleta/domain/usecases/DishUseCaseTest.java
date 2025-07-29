package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import com.plazoleta.plazoleta.plazoleta.domain.model.CategoryModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.CategoryPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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

    private DishUseCase dishUseCase;

    private static final Long MOCK_OWNER_ID = 1L;

    private DishModel dishModel;
    private RestaurantModel restaurantModel;

    @BeforeEach
    void setUp() {
        dishUseCase = new DishUseCase(dishPersistencePort, restaurantPersistencePort, categoryPersistencePort);

        restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);
        restaurantModel.setOwnerId(MOCK_OWNER_ID);

        CategoryModel categoryModel = new CategoryModel();
        categoryModel.setId(1L);

        dishModel = new DishModel();
        dishModel.setId(10L);
        dishModel.setName("  Plato de Prueba  ");
        dishModel.setPrice(new BigDecimal("25000.00"));
        dishModel.setDescription("  Una descripción  ");
        dishModel.setImageUrl("  https://example.com/image.png  ");
        dishModel.setRestaurant(restaurantModel);
        dishModel.setCategory(categoryModel);
    }

    @Test
    void createDish_withValidData_shouldSucceed() {
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurantModel));
        when(categoryPersistencePort.getCategoryById(1L)).thenReturn(Optional.of(dishModel.getCategory()));

        dishUseCase.createDish(dishModel);

        verify(restaurantPersistencePort, times(2)).getRestaurantById(1L);
        verify(dishPersistencePort).saveDish(any(DishModel.class));
    }

    @Test
    void updateDish_withValidData_shouldSucceed() {
        BigDecimal newPrice = new BigDecimal("30000.00");
        String newDescription = "Nueva descripción";

        when(dishPersistencePort.getDishById(dishModel.getId())).thenReturn(Optional.of(dishModel));
        when(restaurantPersistencePort.getRestaurantById(restaurantModel.getId())).thenReturn(Optional.of(restaurantModel));

        dishUseCase.updateDish(dishModel.getId(), newDescription, newPrice);

        ArgumentCaptor<DishModel> dishCaptor = ArgumentCaptor.forClass(DishModel.class);
        verify(dishPersistencePort).updateDish(dishCaptor.capture());
        DishModel updatedDish = dishCaptor.getValue();

        assertEquals(newPrice, updatedDish.getPrice());
        assertEquals(newDescription, updatedDish.getDescription());
    }

    @Test
    void updateDish_withMismatchedOwner_shouldThrowUnauthorizedUserException() {
        RestaurantModel wrongOwnerRestaurant = new RestaurantModel();
        wrongOwnerRestaurant.setId(1L);
        wrongOwnerRestaurant.setOwnerId(999L);
        dishModel.setRestaurant(wrongOwnerRestaurant);

        when(dishPersistencePort.getDishById(dishModel.getId())).thenReturn(Optional.of(dishModel));
        when(restaurantPersistencePort.getRestaurantById(wrongOwnerRestaurant.getId())).thenReturn(Optional.of(wrongOwnerRestaurant));

        assertThrows(UnauthorizedUserException.class, () -> {
            dishUseCase.updateDish(dishModel.getId(), "desc", new BigDecimal("100"));
        });

        verify(dishPersistencePort, never()).updateDish(any());
    }

    @Test
    void updateDish_whenDishNotFound_shouldThrowDishNotFoundException() {
        when(dishPersistencePort.getDishById(anyLong())).thenReturn(Optional.empty());

        assertThrows(DishNotFoundException.class, () -> {
            dishUseCase.updateDish(99L, "desc", new BigDecimal("100"));
        });
    }
}