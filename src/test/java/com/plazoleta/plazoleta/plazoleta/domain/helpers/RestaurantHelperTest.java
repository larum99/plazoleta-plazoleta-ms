package com.plazoleta.plazoleta.plazoleta.domain.helpers;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.UserValidationPort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;
import com.plazoleta.plazoleta.plazoleta.domain.utils.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantHelperTest {

    @Mock
    private RestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private UserValidationPort userValidationPort;

    @InjectMocks
    private RestaurantHelper restaurantHelper;

    private RestaurantModel restaurant;

    @BeforeEach
    void setUp() {
        restaurant = new RestaurantModel(1L, "  Restaurante Test  ", "123456789", "  Calle 123  ", "  +573001234567  ", "  https://logo.url/img.png  ", 1L);
    }

    @Test
    void normalizeFields_shouldTrimAllStringFields() {
        restaurantHelper.normalizeFields(restaurant);

        assertEquals("Restaurante Test", restaurant.getName());
        assertEquals("123456789", restaurant.getNit());
        assertEquals("Calle 123", restaurant.getAddress());
        assertEquals("+573001234567", restaurant.getPhone());
        assertEquals("https://logo.url/img.png", restaurant.getLogoUrl());
    }

    @Test
    void validateMandatoryFields_withNullName_shouldThrowMissingFieldException() {
        restaurant.setName(null);
        assertThrows(MissingFieldException.class, () -> restaurantHelper.validateMandatoryFields(restaurant));
    }

    @Test
    void validateNit_withInvalidCharacters_shouldThrowInvalidNitException() {
        assertThrows(InvalidNitException.class, () -> restaurantHelper.validateNit("123ABC456"));
    }

    @Test
    void checkIfNitAlreadyExists_whenNitExists_shouldThrowDuplicateNitException() {
        when(restaurantPersistencePort.getRestaurantByNit("123456789")).thenReturn(new RestaurantModel());
        assertThrows(DuplicateNitException.class, () -> restaurantHelper.checkIfNitAlreadyExists("123456789"));
    }

    @Test
    void checkIfNitAlreadyExists_whenNitDoesNotExist_shouldNotThrowException() {
        when(restaurantPersistencePort.getRestaurantByNit(anyString())).thenReturn(null);
        assertDoesNotThrow(() -> restaurantHelper.checkIfNitAlreadyExists("987654321"));
    }

    @Test
    void validatePhone_withInvalidFormat_shouldThrowInvalidPhoneException() {
        assertThrows(InvalidPhoneException.class, () -> restaurantHelper.validatePhone("123ABC789"));
    }

    @Test
    void validateName_withOnlyNumbers_shouldThrowInvalidRestaurantNameException() {
        assertThrows(InvalidRestaurantNameException.class, () -> restaurantHelper.validateName("12345"));
    }

    @Test
    void validateLogoUrl_withInvalidUrl_shouldThrowInvalidLogoUrlException() {
        assertThrows(InvalidLogoUrlException.class, () -> restaurantHelper.validateLogoUrl("invalid-url"));
    }

    @Test
    void validateOwnerExistsAndHasRole_withValidOwner_shouldNotThrowException() {
        UserModel owner = new UserModel(1L, "PROPIETARIO");
        when(userValidationPort.getUserById(1L)).thenReturn(Optional.of(owner));

        assertDoesNotThrow(() -> restaurantHelper.validateOwnerExistsAndHasRole(1L));
    }

    @Test
    void validateOwnerExistsAndHasRole_whenOwnerNotFound_shouldThrowUserNotFoundException() {
        when(userValidationPort.getUserById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> restaurantHelper.validateOwnerExistsAndHasRole(1L));
    }

    @Test
    void validateOwnerExistsAndHasRole_whenUserIsNotOwner_shouldThrowInvalidOwnerException() {
        UserModel clientUser = new UserModel(1L, "CLIENTE");
        when(userValidationPort.getUserById(1L)).thenReturn(Optional.of(clientUser));

        assertThrows(InvalidOwnerException.class, () -> restaurantHelper.validateOwnerExistsAndHasRole(1L));
    }
}