package com.plazoleta.plazoleta.plazoleta.infrastructure.exceptionhandlers;

import java.time.LocalDateTime;

public record ExceptionResponse(String message, LocalDateTime timeStamp) {
}
