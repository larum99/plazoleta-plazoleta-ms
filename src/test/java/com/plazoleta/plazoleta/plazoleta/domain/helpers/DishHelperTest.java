package com.plazoleta.plazoleta.plazoleta.domain.helpers;

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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishHelperTest {

    @Mock
    private DishPersistencePort dishPersistencePort;
    @Mock
    private RestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private CategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private DishHelper dishHelper;

    private DishModel dishModel;
    private static final String OWNER_ROLE = "PROPIETARIO";
    private static final String NON_OWNER_ROLE = "CLIENTE";


    @BeforeEach
    void setUp() {
        dishModel = new DishModel();
        dishModel.setName(" Hamburguesa Clásica ");
        dishModel.setPrice(new BigDecimal("15000.00"));
        dishModel.setDescription(" Deliciosa carne con queso y vegetales ");
        dishModel.setImageUrl(" https://example.com/burger.png ");

        RestaurantModel restaurant = new RestaurantModel();
        restaurant.setId(1L);
        dishModel.setRestaurant(restaurant);

        CategoryModel category = new CategoryModel();
        category.setId(1L);
        dishModel.setCategory(category);
    }

    @Test
    void normalizeFields_shouldTrimAllStringFields() {
        dishHelper.normalizeFields(dishModel);

        assertEquals("Hamburguesa Clásica", dishModel.getName());
        assertEquals("Deliciosa carne con queso y vegetales", dishModel.getDescription());
        assertEquals("https://example.com/burger.png", dishModel.getImageUrl());
    }

    @Test
    void validateMandatoryFields_withValidDish_shouldNotThrowException() {
        assertDoesNotThrow(() -> dishHelper.validateMandatoryFields(dishModel));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "\t", "\n"})
    void validateMandatoryFields_withInvalidName_shouldThrowMissingFieldException(String invalidName) {
        dishModel.setName(invalidName);
        assertThrows(MissingFieldException.class, () -> dishHelper.validateMandatoryFields(dishModel));
    }

    @Test
    void validateRole_withOwnerRole_shouldNotThrowException() {
        assertDoesNotThrow(() -> dishHelper.validateRole(OWNER_ROLE));
    }

    @Test
    void validateRole_withNonOwnerRole_shouldThrowUnauthorizedUserException() {
        assertThrows(UnauthorizedUserException.class, () -> dishHelper.validateRole(NON_OWNER_ROLE));
    }

    @Test
    void validateRestaurantExists_whenNotFound_shouldThrowRestaurantNotFoundException() {
        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class, () -> dishHelper.validateRestaurantExists(1L));
    }

    @Test
    void validateCategoryExists_whenNotFound_shouldThrowCategoryNotFoundException() {
        when(categoryPersistencePort.getCategoryById(1L)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> dishHelper.validateCategoryExists(1L));
    }

    @Test
    void validateOwnership_withMismatchedOwner_shouldThrowUnauthorizedUserException() {
        Long ownerId = 1L;
        Long wrongOwnerId = 2L;
        RestaurantModel restaurant = new RestaurantModel();
        restaurant.setOwnerId(ownerId);

        when(restaurantPersistencePort.getRestaurantById(1L)).thenReturn(Optional.of(restaurant));

        assertThrows(UnauthorizedUserException.class, () -> dishHelper.validateOwnership(1L, wrongOwnerId));
    }

    @Test
    void validatePrice_withZeroPrice_shouldThrowInvalidPriceException() {
        assertThrows(InvalidPriceException.class, () -> dishHelper.validatePrice(BigDecimal.ZERO));
    }

    @Test
    void validatePrice_withNegativePrice_shouldThrowInvalidPriceException() {
        assertThrows(InvalidPriceException.class, () -> dishHelper.validatePrice(new BigDecimal("-100")));
    }
}