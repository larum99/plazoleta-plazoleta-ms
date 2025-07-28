package com.plazoleta.plazoleta.plazoleta.infrastructure.utils.constants;

public class ControllerConstants {

    private ControllerConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String BASE_URL = "/api/v1/plazoleta";
    public static final String SAVE_PATH_RESTAURANT = "/restaurant";
    public static final String TAG = "Restaurantes - Plazoletas";
    public static final String TAG_DESCRIPTION = "Operaciones relacionadas con los restaurantes.";

    public static final String TAG_DISH = "Platos";
    public static final String TAG_DISH_DESCRIPTION = "Operaciones relacionadas con platos";
    public static final String SAVE_DISH_PATH = "/dish";
}
