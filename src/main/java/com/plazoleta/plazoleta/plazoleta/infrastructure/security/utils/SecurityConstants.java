package com.plazoleta.plazoleta.plazoleta.infrastructure.security.utils;

public class SecurityConstants {

    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ROLE_CLAIM = "role";
    public static final String ID_CLAIM = "id";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String[] PUBLIC_PATHS = {
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/v3/api-docs",
            "/api/v1/plazoleta/**"
    };

    public static final String RESTAURANT_PROTECTED_PATH = "/api/v1/plazoleta/restaurant";
    public static final String DISH_PROTECTED_PATH = "/api/v1/plazoleta/dish";

    public static final String APPLICATION_JSON = "application/json";

    public static final String ACCESS_DENIED_MESSAGE_TEMPLATE = """
        {
          "message": "Acceso denegado: no tienes los permisos necesarios.",
          "timestamp": "%s"
        }
    """;

    public static final String ORDER_PROTECTED_PATH = "/api/v1/plazoleta/orders";
}
