package com.plazoleta.plazoleta.commons.configurations.swagger.examples;

public class OrderSwaggerExamples {

    private OrderSwaggerExamples() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CREATE_ORDER_REQUEST = """
        {
            "restaurantId": 1,
            "dishes": [
                {
                    "dish": {
                        "id": 5
                    },
                    "quantity": 2
                },
                {
                    "dish": {
                        "id": 8
                    },
                    "quantity": 1
                }
            ]
        }
    """;

    public static final String ORDER_CREATED_RESPONSE = """
        {
            "message": "Pedido creado exitosamente.",
            "time": "2025-08-03T12:30:00"
        }
    """;

    public static final String INVALID_ORDER_RESPONSE = """
        {
            "message": "Uno o más platos no pertenecen al restaurante especificado.",
            "time": "2025-08-03T12:31:00"
        }
    """;
}
