package com.plazoleta.plazoleta.plazoleta.infrastructure.utils.constants;

public class FeignConstants {

    private FeignConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NAME_SERVICE = "user-service";
    public static final String URL_SERVICE = "${microservices.users.url}";
    public static final String GET_BY_EMAIL_PATH = "/api/v1/users/email";
    public static final String GET_BY_ID_PATH = "/api/v1/users/{id}";
    public static final String EMAIL_PARAM = "email";
    public static final String ID_PATH_VARIABLE = "id";

    public static final String MESSAGING_NAME_SERVICE = "messaging-service";
    public static final String MESSAGING_URL_SERVICE = "${microservices.messaging.url}";
    public static final String SEND_ORDER_READY_MESSAGE_PATH = "/api/v1/messaging/send-code";
}
