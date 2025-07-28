package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.CategoryNotFoundException;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.InvalidPriceException;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.MissingFieldException;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.RestaurantNotFoundException;
import com.plazoleta.plazoleta.plazoleta.domain.model.CategoryModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.CategoryPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
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

    @InjectMocks
    private DishUseCase dishUseCase;

    private DishModel dishModel;
    private RestaurantModel restaurantModel;
    private CategoryModel categoryModel;

    @BeforeEach
    void setUp() {
        restaurantModel = new RestaurantModel();
        restaurantModel.setId(1L);

        categoryModel = new CategoryModel();
        categoryModel.setId(1L);

        dishModel = new DishModel();
        dishModel.setName("Plato de Prueba");
        dishModel.setPrice(new BigDecimal("25000.00"));
        dishModel.setDescription("Una descripción deliciosa");
        dishModel.setImageUrl("https://example.com/image.png");
        dishModel.setRestaurant(restaurantModel);
        dishModel.setCategory(categoryModel);
        dishModel.setActive(true);
    }

    @Test
    void createDish_withValidData_shouldSaveDish() {
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(new RestaurantModel()));
        when(categoryPersistencePort.getCategoryById(1L)).thenReturn(Optional.of(new CategoryModel()));

        dishUseCase.createDish(dishModel);

        verify(restaurantPersistencePort).getRestaurantById(1L);
        verify(categoryPersistencePort).getCategoryById(1L);
        verify(dishPersistencePort).saveDish(any(DishModel.class));
    }

    @Test
    void createDish_withMissingName_shouldThrowMissingFieldException() {
        dishModel.setName(null);
        assertThrows(MissingFieldException.class, () -> dishUseCase.createDish(dishModel));
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void createDish_withMissingPrice_shouldThrowMissingFieldException() {
        dishModel.setPrice(null);
        assertThrows(MissingFieldException.class, () -> dishUseCase.createDish(dishModel));
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void createDish_withMissingDescription_shouldThrowMissingFieldException() {
        dishModel.setDescription("");
        assertThrows(MissingFieldException.class, () -> dishUseCase.createDish(dishModel));
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void createDish_withMissingRestaurantId_shouldThrowMissingFieldException() {
        dishModel.getRestaurant().setId(null);
        assertThrows(MissingFieldException.class, () -> dishUseCase.createDish(dishModel));
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void createDish_withMissingCategoryId_shouldThrowMissingFieldException() {
        dishModel.getCategory().setId(null);
        assertThrows(MissingFieldException.class, () -> dishUseCase.createDish(dishModel));
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void createDish_withZeroPrice_shouldThrowInvalidPriceException() {
        dishModel.setPrice(BigDecimal.ZERO);
        assertThrows(InvalidPriceException.class, () -> dishUseCase.createDish(dishModel));
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void createDish_withNegativePrice_shouldThrowInvalidPriceException() {
        dishModel.setPrice(new BigDecimal("-100"));
        assertThrows(InvalidPriceException.class, () -> dishUseCase.createDish(dishModel));
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void createDish_whenRestaurantNotFound_shouldThrowRestaurantNotFoundException() {
        when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class, () -> dishUseCase.createDish(dishModel));
        verify(dishPersistencePort, never()).saveDish(any());
    }

    @Test
    void createDish_whenCategoryNotFound_shouldThrowCategoryNotFoundException() {
        when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(Optional.of(new RestaurantModel()));
        when(categoryPersistencePort.getCategoryById(anyLong())).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () -> dishUseCase.createDish(dishModel));
        verify(dishPersistencePort, never()).saveDish(any());
    }
}