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

    public static final String SAVE_ORDER_PATH = "/orders";

    public static final String ROLE_CLIENT = "hasRole('CLIENTE')";

    public static final String SAVE_PATH_EMPLOYEE_RESTAURANT = "/employee-restaurant";

    public static final String LIST_ORDERS_PATH = "/orders";

    public static final String ROLE_EMPLOYEE = "hasRole('EMPLEADO')";

    public static final String UPDATE_ORDER_STATUS_PATH = "/orders/{orderId}/status";

    public static final String CANCEL_ORDER_PATH = "/orders/{orderId}/cancel";

    public static final String CANCEL_ORDERS_RESTAURANT_PATH = "/orders/restaurant";

    public static final String SUMMARY_GET_ORDERS_BY_RESTAURANT = "Obtener IDs de pedidos por restaurante";
    public static final String DESCRIPTION_GET_ORDERS_BY_RESTAURANT = "Permite al propietario de un restaurante obtener los IDs de los pedidos asociados a su restaurante.";
    public static final String DESCRIPTION_GET_ORDERS_BY_RESTAURANT_SUCCESS = "IDs de pedidos obtenidos exitosamente.";
    public static final String SUMMARY_GET_ORDERS_BY_RESTAURANT_SUCCESS = "Lista de IDs de pedidos";
    public static final String DESCRIPTION_GET_ORDERS_BY_RESTAURANT_BAD_REQUEST = "Solicitud inválida para obtener IDs de pedidos por restaurante.";
    public static final String DESCRIPTION_GET_ORDERS_BY_RESTAURANT_UNAUTHORIZED = "No autorizado para obtener IDs de pedidos por restaurante.";

    public static final String RESTAURANT_EXISTS_PATH = "/exists";

}
