package com.plazoleta.plazoleta.plazoleta.domain.exceptions;

public class MissingFieldException extends RuntimeException {
    public MissingFieldException(String message) {
        super(message);
    }
}
