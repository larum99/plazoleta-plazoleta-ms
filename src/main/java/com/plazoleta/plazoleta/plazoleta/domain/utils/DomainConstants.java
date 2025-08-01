package com.plazoleta.plazoleta.plazoleta.domain.utils;

import java.math.BigDecimal;

public class DomainConstants {

    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final int PHONE_MAX_LENGTH = 13;

    public static final String ERROR_REQUIRED_RESTAURANT_NAME = "El nombre del restaurante es obligatorio";
    public static final String ERROR_REQUIRED_NIT = "El NIT del restaurante es obligatorio";
    public static final String ERROR_REQUIRED_ADDRESS = "La dirección del restaurante es obligatoria";
    public static final String ERROR_REQUIRED_PHONE = "El teléfono del restaurante es obligatorio";
    public static final String ERROR_REQUIRED_LOGO_URL = "La URL del logo es obligatoria";
    public static final String ERROR_REQUIRED_OWNER_ID = "El ID del propietario es obligatorio";

    public static final String ROLE_OWNER = "PROPIETARIO";

    public static final String ERROR_REQUIRED_DISH_NAME = "El nombre del plato es obligatorio";
    public static final String ERROR_REQUIRED_DISH_PRICE = "El precio del plato es obligatorio";
    public static final String ERROR_REQUIRED_DISH_DESCRIPTION = "La descripción del plato es obligatoria";
    public static final String ERROR_REQUIRED_RESTAURANT_ID = "El ID del restaurante es obligatorio";
    public static final String ERROR_REQUIRED_CATEGORY_ID = "El ID de la categoría es obligatorio";
    public static final String ERROR_REQUIRED_DISH_ID = "El ID del plato es obligatorio";

    public static final String ROLE_ADMIN = "ADMINISTRADOR";

    public static final int DEFAULT_PAGE_NUMBER = 0;
    public static final int DEFAULT_SIZE_NUMBER = 1;
}
