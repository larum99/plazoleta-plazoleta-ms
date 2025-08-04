package com.plazoleta.plazoleta.plazoleta.domain.exceptions;

public class RestaurantDoesNotBelongToOwnerException extends RuntimeException {
  public RestaurantDoesNotBelongToOwnerException(String message) {
    super(message);
  }
}
