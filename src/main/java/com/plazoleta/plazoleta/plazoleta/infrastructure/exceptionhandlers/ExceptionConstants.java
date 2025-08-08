package com.plazoleta.plazoleta.plazoleta.infrastructure.exceptionhandlers;

public class ExceptionConstants {

    private ExceptionConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String INVALID_NIT_MESSAGE = "El NIT proporcionado no es válido. Debe contener solo números.";
    public static final String INVALID_PHONE_MESSAGE = "El número de teléfono del restaurante debe tener máximo 13 caracteres y puede iniciar con '+'.";
    public static final String INVALID_NAME_MESSAGE = "El nombre del restaurante no puede contener solo números.";
    public static final String INVALID_LOGO_URL_MESSAGE = "La URL del logo no es válida.";
    public static final String DUPLICATE_NIT_MESSAGE = "Ya existe un restaurante registrado con este NIT.";
    public static final String INVALID_OWNER_MESSAGE = "El ID del propietario no corresponde a un usuario con rol PROPIETARIO.";
    public static final String USER_NOT_FOUND_MESSAGE = "El usuario con el ID proporcionado no fue encontrado.";

    public static final String INVALID_PRICE_MESSAGE = "El precio del plato debe ser mayor a 0.";
    public static final String RESTAURANT_NOT_FOUND_MESSAGE = "El restaurante con el ID proporcionado no fue encontrado.";
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "La categoría con el ID proporcionado no fue encontrada.";
    public static final String UNAUTHORIZED_USER_MESSAGE = "Solo el propietario del restaurante puede realizar esta acción.";
    public static final String DISH_NOT_FOUND_MESSAGE = "El plato con el ID proporcionado no fue encontrado.";
    public static final String FORBIDDEN_OPERATION_MESSAGE = "No tienes permisos para realizar esta operación.";
    public static final String CLIENT_HAS_ACTIVE_ORDER_MESSAGE = "El cliente ya tiene un pedido activo.";
    public static final String DISH_NOT_BELONG_RESTAURANT_MESSAGE = "El plato no pertenece al restaurante especificado.";
    public static final String EMPLOYEE_ALREADY_ASSIGNED_MESSAGE = "El empleado ya está asignado a un restaurante.";
    public static final String INVALID_EMPLOYEE_MESSAGE = "El ID del empleado no es válido o no corresponde a un usuario con rol EMPLEADO.";
    public static final String INVALID_RESTAURANT_ASSIGNMENT_MESSAGE = "El empleado no pertenece al restaurante de la orden.";
    public static final String ORDER_ALREADY_ASSIGNED_MESSAGE = "La orden ya ha sido asignada a un empleado.";
    public static final String INVALID_ORDER_STATUS_MESSAGE = "El estado de la orden no es válido para esta operación.";
    public static final String UNAUTHORIZED_ORDER_ACCESS_MESSAGE = "No tienes permisos para acceder a esta orden.";
}
