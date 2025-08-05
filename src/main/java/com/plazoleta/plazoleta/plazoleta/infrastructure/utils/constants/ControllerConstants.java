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
    public static final String UPDATE_DISH_PATH = "/dish/{id}";

    public static final String ROLE_PROPIETARIO = "hasRole('PROPIETARIO')";
    public static final String ROLE_ADMINISTRADOR = "hasRole('ADMINISTRADOR')";
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String UPDATE_STATUS_DISH_PATH = "/dishes/{id}/status";

    public static final String LIST_PATH_RESTAURANTS = "/restaurants";

    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "10";

    public static final String LIST_DISHES_PATH = "/dishes";

    public static final String SAVE_ORDER_PATH = "/order";

    public static final String ROLE_CLIENT = "hasRole('CLIENTE')";

    public static final String ROLE_EMPLOYEE = "hasRole('EMPLEADO')";

    public static final String LIST_ORDERS_PATH = "/orders";
}
