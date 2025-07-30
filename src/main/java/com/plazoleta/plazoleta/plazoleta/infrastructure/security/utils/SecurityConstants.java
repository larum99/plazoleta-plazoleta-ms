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
            "/v3/api-docs"
    };

    public static final String RESTAURANT_PROTECTED_PATH = "/api/v1/restaurant/";
    public static final String DISH_PROTECTED_PATH = "/api/v1/dish/";
}
