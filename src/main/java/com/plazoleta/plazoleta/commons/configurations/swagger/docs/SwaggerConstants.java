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

    public static final String DESCRIPTION_CREATE_RESTAURANT_SUCCESS = "Restaurante creado exitosamente";
    public static final String DESCRIPTION_RESTAURANT_ALREADY_EXISTS = "Datos inválidos o restaurante ya existente";

    public static final String SUMMARY_CREATE_RESTAURANT = "Crear un nuevo restaurante";
    public static final String DESCRIPTION_CREATE_RESTAURANT = "Permite registrar un nuevo restaurante en la plataforma.";

    public static final String EXAMPLE_NAME_SUCCESS = "Respuesta exitosa";
    public static final String EXAMPLE_NAME_VALIDATION_ERROR = "Error de validación";
    public static final String EXAMPLE_NAME_CREATE_REQUEST = "Ejemplo de creación";

    public static final String SUMMARY_RESTAURANT_CREATED = "Restaurante creado";
    public static final String SUMMARY_RESTAURANT_DUPLICATE = "Restaurante duplicado";
    public static final String SUMMARY_CREATE_RESTAURANT_EXAMPLE = "Creación de restaurante";
}
