package com.plazoleta.plazoleta.commons.configurations.swagger.examples;

public class RestaurantSwaggerExamples {

    private RestaurantSwaggerExamples() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CREATE_RESTAURANT_REQUEST = """
        {
          "name": "Restaurante La Casona",
          "nit": "1234567890",
          "address": "Calle 123 #45-67",
          "phone": "3123456789",
          "logoUrl": "https://imagenes.la-casona.com/logo.png",
          "ownerId": 5
        }
    """;

    public static final String RESTAURANT_CREATED_RESPONSE = """
        {
          "message": "Restaurante creado correctamente.",
          "time": "2025-07-25T15:00:00"
        }
    """;

    public static final String RESTAURANT_ALREADY_EXISTS_RESPONSE = """
        {
          "message": "El restaurante ya existe.",
          "time": "2025-07-25T15:01:00"
        }
    """;
}
