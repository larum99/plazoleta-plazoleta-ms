package com.plazoleta.plazoleta.commons.configurations.swagger.examples;

public class DishSwaggerExamples {

    private DishSwaggerExamples() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CREATE_DISH_REQUEST = """
        {
          "name": "Bandeja Paisa",
          "description": "Plato típico colombiano con arroz, carne, huevo, chorizo, arepa, aguacate y plátano maduro.",
          "price": 25000,
          "imageUrl": "https://imagenes.platos.com/bandeja-paisa.png",
          "restaurantId": 1,
          "categoryId": 3,
          "active": true
        }
    """;

    public static final String DISH_CREATED_RESPONSE = """
        {
          "message": "Plato creado correctamente.",
          "time": "2025-07-27T12:00:00"
        }
    """;

    public static final String DISH_ALREADY_EXISTS_RESPONSE = """
        {
          "message": "El plato ya existe.",
          "time": "2025-07-27T12:01:00"
        }
    """;
}
