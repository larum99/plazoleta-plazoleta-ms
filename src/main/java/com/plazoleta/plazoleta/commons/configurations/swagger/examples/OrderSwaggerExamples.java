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
                      "dishId": 5,
                      "quantity": 2
                    },
                    {
                      "dishId": 8,
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

    public static final String ORDER_LIST_RESPONSE = """
        {
        "content": [
            {
                "id": 1,
                "clientId": 10,
                "restaurant": {
                    "id": 1,
                    "name": "Sabor Criollo"
            },
                "dishes": [
                {
                    "dishId": 5,
                    "quantity": 2
                }
            ],
            "status": "PENDIENTE",
            "date": "2025-08-05T12:00:00",
            "assignedEmployeeId": null
            }
        ],
        "totalPages": 1,
        "totalElements": 1,
        "size": 10,
        "number": 0
        }
    """;

    public static final String ORDER_UPDATE_REQUEST = """
        {
            "status": "EN_PREPARACION"
        }
    """;

    public static final String ORDER_UPDATE_WITH_CODE_REQUEST = """
        {
            "status": "ENTREGADO",
            "code": "123456"
        }
    """;


    public static final String ORDER_UPDATED_RESPONSE = """
        {
            "message": "Pedido actualizado correctamente.",
            "time": "2025-08-05T12:45:00"
        }
    """;

    public static final String ORDER_NOT_FOUND_RESPONSE = """
        {
            "message": "Pedido no encontrado."
        }
    """;

    public static final String ORDER_ALREADY_ASSIGNED_RESPONSE = """
        {
            "message": "Este pedido ya fue asignado a otro empleado."
           }
    """;

}
