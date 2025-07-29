package com.plazoleta.plazoleta.plazoleta.domain.helpers;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.InvalidPriceException;
import com.plazoleta.plazoleta.plazoleta.domain.exceptions.MissingFieldException;
import com.plazoleta.plazoleta.plazoleta.domain.model.CategoryModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class DishHelperTest {

    private DishHelper dishHelper;
    private DishModel dishModel;

    @BeforeEach
    void setUp() {
        dishHelper = new DishHelper();

        dishModel = new DishModel();
        dishModel.setName("  Hamburguesa Clásica  ");
        dishModel.setPrice(new BigDecimal("15000.00"));
        dishModel.setDescription("  Deliciosa carne con queso y vegetales  ");
        dishModel.setImageUrl("  https://example.com/burger.png  ");
        dishModel.setRestaurant(new RestaurantModel());
        dishModel.setCategory(new CategoryModel());

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
    @ValueSource(strings = {"  ", "\t", "\n"})
    void validateMandatoryFields_withInvalidName_shouldThrowMissingFieldException(String invalidName) {
        dishModel.setName(invalidName);
        assertThrows(MissingFieldException.class, () -> dishHelper.validateMandatoryFields(dishModel));
    }

    @Test
    void validateMandatoryFields_withNullPrice_shouldThrowMissingFieldException() {
        dishModel.setPrice(null);
        assertThrows(MissingFieldException.class, () -> dishHelper.validateMandatoryFields(dishModel));
    }

    @Test
    void validatePrice_withPositivePrice_shouldNotThrowException() {
        assertDoesNotThrow(() -> dishHelper.validatePrice(new BigDecimal("1.0")));
    }

    @Test
    void validatePrice_withZeroPrice_shouldThrowInvalidPriceException() {
        assertThrows(InvalidPriceException.class, () -> dishHelper.validatePrice(BigDecimal.ZERO));
    }

    @Test
    void validatePrice_withNegativePrice_shouldThrowInvalidPriceException() {
        assertThrows(InvalidPriceException.class, () -> dishHelper.validatePrice(new BigDecimal("-100")));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  "})
    void validateDescription_withInvalidDescription_shouldThrowMissingFieldException(String invalidDescription) {
        assertThrows(MissingFieldException.class, () -> dishHelper.validateDescription(invalidDescription));
    }
}