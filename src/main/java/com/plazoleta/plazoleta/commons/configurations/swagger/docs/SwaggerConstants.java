package com.plazoleta.plazoleta.commons.configurations.swagger.docs;

public class SwaggerConstants {

    private SwaggerConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String APPLICATION_JSON = "application/json";
    public static final String CREATED = "201";
    public static final String OK = "200";
    public static final String BAD_REQUEST = "400";
    public static final String NOT_FOUND = "404";
    public static final String EXAMPLE_NAME_SUCCESS = "Respuesta exitosa";
    public static final String EXAMPLE_NAME_ERROR = "Error";
    public static final String EXAMPLE_NAME_CREATE_REQUEST = "Ejemplo de creación";
    public static final String EXAMPLE_NAME_UPDATE_REQUEST = "Ejemplo de actualización";
    public static final String EXAMPLE_NAME_VALIDATION_ERROR = "Error de validación";
    public static final String EXAMPLE_NAME_NOT_FOUND = "No encontrado";

    public static final String SUMMARY_CREATE_RESTAURANT = "Crear un nuevo restaurante";
    public static final String DESCRIPTION_CREATE_RESTAURANT = "Permite registrar un nuevo restaurante en la plataforma.";
    public static final String DESCRIPTION_CREATE_RESTAURANT_SUCCESS = "Restaurante creado exitosamente";
    public static final String DESCRIPTION_RESTAURANT_ALREADY_EXISTS = "Datos inválidos o restaurante ya existente";
    public static final String SUMMARY_CREATE_RESTAURANT_EXAMPLE = "Creación de restaurante";
    public static final String SUMMARY_RESTAURANT_CREATED = "Restaurante creado";
    public static final String SUMMARY_RESTAURANT_DUPLICATE = "Restaurante duplicado";

    public static final String SUMMARY_CREATE_DISH = "Crear un nuevo plato";
    public static final String DESCRIPTION_CREATE_DISH = "Permite registrar un nuevo plato en la plataforma.";
    public static final String REQUEST_BODY_CREATE_DISH = "Datos del plato a crear";
    public static final String DESCRIPTION_CREATE_DISH_SUCCESS = "Plato creado exitosamente";
    public static final String DESCRIPTION_DISH_ALREADY_EXISTS = "Datos inválidos o plato ya existente";
    public static final String SUMMARY_CREATE_DISH_EXAMPLE = "Plato nuevo";
    public static final String SUMMARY_DISH_CREATED = "Plato creado";
    public static final String SUMMARY_DISH_DUPLICATE = "Plato duplicado";

    public static final String SUMMARY_UPDATE_DISH = "Actualizar un plato existente";
    public static final String DESCRIPTION_UPDATE_DISH = "Permite modificar el precio y la descripción de un plato, si pertenece al restaurante del propietario.";
    public static final String REQUEST_BODY_UPDATE_DISH = "Datos del plato a actualizar";
    public static final String DESCRIPTION_UPDATE_DISH_SUCCESS = "Plato actualizado exitosamente";
    public static final String SUMMARY_UPDATE_DISH_EXAMPLE = "Actualización parcial de plato";
    public static final String SUMMARY_DISH_UPDATED = "Actualización correcta";

    public static final String SUMMARY_UPDATE_DISH_STATUS = "Actualizar el estado de un plato";
    public static final String DESCRIPTION_UPDATE_DISH_STATUS = "Permite habilitar o deshabilitar un plato si pertenece al restaurante del propietario.";
    public static final String PARAM_ID_DESCRIPTION = "ID del plato a actualizar";
    public static final String PARAM_ID_EXAMPLE = "10";
    public static final String PARAM_STATUS_DESCRIPTION = "Nuevo estado del plato (true para habilitado, false para deshabilitado)";
    public static final String PARAM_STATUS_EXAMPLE = "false";
    public static final String DESCRIPTION_INVALID_OR_UNAUTHORIZED = "Parámetros inválidos o plato no pertenece al propietario";
    public static final String SUMMARY_UNAUTHORIZED_OR_INVALID = "No autorizado o datos inválidos";
    public static final String DESCRIPTION_DISH_NOT_FOUND = "Plato no encontrado";
    public static final String SUMMARY_ID_NOT_FOUND = "ID inexistente";

    public static final String SUMMARY_LIST_RESTAURANTS = "Listar restaurantes";
    public static final String DESCRIPTION_LIST_RESTAURANTS = "Devuelve una lista paginada de restaurantes, ordenada alfabéticamente por nombre.";
    public static final String RESPONSE_LIST_RESTAURANTS_SUCCESS = "Listado de restaurantes obtenido correctamente";

    public static final String SUMMARY_LIST_DISHES = "Listar platos filtrados y paginados";
    public static final String DESCRIPTION_LIST_DISHES = "Obtiene una lista de platos según filtros por restaurante y categoría, incluyendo paginación y ordenamiento.";
    public static final String DESCRIPTION_LIST_DISHES_SUCCESS = "Listado de platos obtenido exitosamente.";
    public static final String DESCRIPTION_BAD_REQUEST_MISSING_FILTERS = "Faltan filtros requeridos como restaurantId o categoryId.";

    public static final String EXAMPLE_NAME_LIST = "dishListExample";
    public static final String SUMMARY_DISH_LIST = "Ejemplo de listado de platos";
    public static final String SUMMARY_MISSING_FILTERS = "Faltan filtros obligatorios";

    public static final String SUMMARY_CREATE_ORDER = "Crear un nuevo pedido";
    public static final String DESCRIPTION_CREATE_ORDER = "Permite a un cliente realizar un nuevo pedido con platos de un restaurante.";
    public static final String DESCRIPTION_CREATE_ORDER_SUCCESS = "Pedido creado exitosamente";
    public static final String SUMMARY_CREATE_ORDER_EXAMPLE = "Ejemplo de creación de pedido";
    public static final String SUMMARY_ORDER_CREATED = "Pedido creado correctamente";
    public static final String DESCRIPTION_ORDER_VALIDATION_ERROR = "Datos inválidos o platos no pertenecen al restaurante";
    public static final String SUMMARY_ORDER_INVALID = "Pedido inválido";

}