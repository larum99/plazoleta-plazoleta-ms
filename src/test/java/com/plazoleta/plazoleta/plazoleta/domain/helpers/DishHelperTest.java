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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
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
    private RestaurantModel restaurantModel;
    private static final String OWNER_ROLE = "PROPIETARIO";
    private static final String NON_OWNER_ROLE = "CLIENTE";
    private static final Long OWNER_ID = 10L;
    private static final Long RESTAURANT_ID = 1L;
    private static final Long DISH_ID = 100L;
    private static final Long CATEGORY_ID = 5L;


    @BeforeEach
    void setUp() {
        restaurantModel = new RestaurantModel();
        restaurantModel.setId(RESTAURANT_ID);
        restaurantModel.setOwnerId(OWNER_ID);

        CategoryModel category = new CategoryModel();
        category.setId(CATEGORY_ID);

        dishModel = new DishModel();
        dishModel.setId(DISH_ID);
        dishModel.setName(" Hamburguesa Clásica ");
        dishModel.setPrice(new BigDecimal("15000.00"));
        dishModel.setDescription(" Deliciosa carne con queso y vegetales ");
        dishModel.setImageUrl(" https://example.com/burger.png ");
        dishModel.setRestaurant(restaurantModel);
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

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void validateMandatoryFields_withInvalidDescription_shouldThrowMissingFieldException(String invalidDescription) {
        dishModel.setDescription(invalidDescription);
        assertThrows(MissingFieldException.class, () -> dishHelper.validateMandatoryFields(dishModel));
    }

    @Test
    void validateMandatoryFields_withNullRestaurantId_shouldThrowMissingFieldException() {
        dishModel.getRestaurant().setId(null);
        assertThrows(MissingFieldException.class, () -> dishHelper.validateMandatoryFields(dishModel));
    }

    @Test
    void validateMandatoryFields_withNullCategoryId_shouldThrowMissingFieldException() {
        dishModel.getCategory().setId(null);
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

    @ParameterizedTest
    @CsvSource({"-100", "0"})
    void validatePrice_withInvalidPrice_shouldThrowInvalidPriceException(String price) {
        assertThrows(InvalidPriceException.class, () -> dishHelper.validatePrice(new BigDecimal(price)));
    }

    @Test
    void validatePrice_withNullPrice_shouldThrowInvalidPriceException() {
        assertThrows(InvalidPriceException.class, () -> dishHelper.validatePrice(null));
    }

    @Test
    void validateDescription_withValidDescription_shouldNotThrow() {
        assertDoesNotThrow(() -> dishHelper.validateDescription("Valid Description"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void validateDescription_withInvalidDescription_shouldThrowMissingFieldException(String description) {
        assertThrows(MissingFieldException.class, () -> dishHelper.validateDescription(description));
    }

    @Test
    void validateRestaurantExists_whenFound_shouldNotThrow() {
        when(restaurantPersistencePort.getRestaurantById(RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));
        assertDoesNotThrow(() -> dishHelper.validateRestaurantExists(RESTAURANT_ID));
    }

    @Test
    void validateRestaurantExists_whenNotFound_shouldThrowRestaurantNotFoundException() {
        when(restaurantPersistencePort.getRestaurantById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RestaurantNotFoundException.class, () -> dishHelper.validateRestaurantExists(999L));
    }

    @Test
    void validateCategoryExists_whenFound_shouldNotThrow() {
        when(categoryPersistencePort.getCategoryById(CATEGORY_ID)).thenReturn(Optional.of(new CategoryModel()));
        assertDoesNotThrow(() -> dishHelper.validateCategoryExists(CATEGORY_ID));
    }

    @Test
    void validateCategoryExists_whenNotFound_shouldThrowCategoryNotFoundException() {
        when(categoryPersistencePort.getCategoryById(anyLong())).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> dishHelper.validateCategoryExists(999L));
    }

    @Test
    void validateOwnership_withCorrectOwner_shouldNotThrow() {
        when(restaurantPersistencePort.getRestaurantById(RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));
        assertDoesNotThrow(() -> dishHelper.validateOwnership(RESTAURANT_ID, OWNER_ID));
    }

    @Test
    void validateOwnership_withMismatchedOwner_shouldThrowUnauthorizedUserException() {
        Long wrongOwnerId = 99L;
        when(restaurantPersistencePort.getRestaurantById(RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));
        assertThrows(UnauthorizedUserException.class, () -> dishHelper.validateOwnership(RESTAURANT_ID, wrongOwnerId));
    }

    @Test
    void getValidatedDishForUpdate_success_shouldReturnDish() {
        when(dishPersistencePort.getDishById(DISH_ID)).thenReturn(Optional.of(dishModel));
        when(restaurantPersistencePort.getRestaurantById(RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));

        DishModel result = dishHelper.getValidatedDishForUpdate(DISH_ID, "New Desc", new BigDecimal("200"), OWNER_ID);

        assertNotNull(result);
        assertEquals(DISH_ID, result.getId());
    }

    @Test
    void getValidatedDishForUpdate_withNullDishId_shouldThrowMissingFieldException() {
        assertThrows(MissingFieldException.class,
                () -> dishHelper.getValidatedDishForUpdate(null, "Desc", new BigDecimal("100"), OWNER_ID));
    }

    @Test
    void getValidatedDishForUpdate_withNullPrice_shouldThrowMissingFieldException() {
        assertThrows(MissingFieldException.class,
                () -> dishHelper.getValidatedDishForUpdate(DISH_ID, "Desc", null, OWNER_ID));
    }

    @Test
    void getValidatedDishForUpdate_whenDishNotFound_shouldThrowDishNotFoundException() {
        when(dishPersistencePort.getDishById(DISH_ID)).thenReturn(Optional.empty());
        assertThrows(DishNotFoundException.class,
                () -> dishHelper.getValidatedDishForUpdate(DISH_ID, "Desc", new BigDecimal("100"), OWNER_ID));
    }

    @Test
    void getValidatedDishForUpdate_whenNotOwner_shouldThrowUnauthorizedUserException() {
        Long wrongOwnerId = 99L;
        when(dishPersistencePort.getDishById(DISH_ID)).thenReturn(Optional.of(dishModel));
        when(restaurantPersistencePort.getRestaurantById(RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));

        assertThrows(UnauthorizedUserException.class,
                () -> dishHelper.getValidatedDishForUpdate(DISH_ID, "Desc", new BigDecimal("100"), wrongOwnerId));
    }

    @Test
    void getValidatedDishForStatusChange_success_shouldReturnDish() {
        when(dishPersistencePort.getDishById(DISH_ID)).thenReturn(Optional.of(dishModel));
        when(restaurantPersistencePort.getRestaurantById(RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));

        DishModel result = dishHelper.getValidatedDishForStatusChange(DISH_ID, OWNER_ID);

        assertNotNull(result);
        assertEquals(DISH_ID, result.getId());
    }

    @Test
    void getValidatedDishForStatusChange_whenDishNotFound_shouldThrowDishNotFoundException() {
        when(dishPersistencePort.getDishById(DISH_ID)).thenReturn(Optional.empty());

        assertThrows(DishNotFoundException.class,
                () -> dishHelper.getValidatedDishForStatusChange(DISH_ID, OWNER_ID));
    }

    @Test
    void getValidatedDishForStatusChange_whenNotOwner_shouldThrowUnauthorizedUserException() {
        Long wrongOwnerId = 99L;
        when(dishPersistencePort.getDishById(DISH_ID)).thenReturn(Optional.of(dishModel));
        when(restaurantPersistencePort.getRestaurantById(RESTAURANT_ID)).thenReturn(Optional.of(restaurantModel));

        assertThrows(UnauthorizedUserException.class,
                () -> dishHelper.getValidatedDishForStatusChange(DISH_ID, wrongOwnerId));
    }
}