package com.plazoleta.plazoleta.plazoleta.infrastructure.exceptionhandlers;

public class ExceptionConstants {

    private ExceptionConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String INVALID_NIT_MESSAGE = "El NIT proporcionado no es válido.";
    public static final String INVALID_PHONE_MESSAGE = "El número de teléfono del restaurante no es válido.";
    public static final String INVALID_NAME_MESSAGE = "El nombre del restaurante no es válido.";
    public static final String INVALID_LOGO_URL_MESSAGE = "La URL del logo no es válida.";
    public static final String DUPLICATE_NIT_MESSAGE = "Ya existe un restaurante registrado con este NIT.";
    public static final String INVALID_OWNER_MESSAGE = "El ID del propietario no corresponde a un usuario con rol PROPIETARIO.";
    public static final String USER_NOT_FOUND_MESSAGE = "El usuario con el ID proporcionado no fue encontrado.";
}
