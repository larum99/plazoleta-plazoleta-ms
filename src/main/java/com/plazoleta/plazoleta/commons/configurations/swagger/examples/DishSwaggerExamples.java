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

    public static final String UPDATE_DISH_REQUEST = """
        {
            "description": "Delicioso plato colombiano con ingredientes frescos.",
            "price": 27000
        }
    """;

    public static final String DISH_UPDATED_RESPONSE = """
        {
            "message": "Plato actualizado correctamente.",
            "time": "2025-07-27T14:35:00"
        }
    """;

    public static final String UNAUTHORIZED_DISH_MODIFICATION_RESPONSE = """
        {
            "message": "No puedes modificar un plato que no te pertenece"
        }
    """;

    public static final String DISH_NOT_FOUND_RESPONSE = """
        {
            "message": "No se encontró un plato con el ID proporcionado"
        }
    """;
}