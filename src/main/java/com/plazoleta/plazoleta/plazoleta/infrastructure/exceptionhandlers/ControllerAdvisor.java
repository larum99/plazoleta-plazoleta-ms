package com.plazoleta.plazoleta.plazoleta.infrastructure.exceptionhandlers;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(InvalidNitException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidNit(InvalidNitException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_NIT_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidPhoneException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidPhone(InvalidPhoneException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_PHONE_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidRestaurantNameException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRestaurantName(InvalidRestaurantNameException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_NAME_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidLogoUrlException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidLogoUrl(InvalidLogoUrlException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_LOGO_URL_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidOwnerException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidOwner(InvalidOwnerException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(
                ExceptionConstants.INVALID_OWNER_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(DuplicateNitException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateNit(DuplicateNitException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(
                ExceptionConstants.DUPLICATE_NIT_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(MissingFieldException.class)
    public ResponseEntity<ExceptionResponse> handleMissingField(MissingFieldException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                ExceptionConstants.USER_NOT_FOUND_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidPriceException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidPrice(InvalidPriceException ex) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                ExceptionConstants.INVALID_PRICE_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(RestaurantNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleRestaurantNotFound(RestaurantNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                ExceptionConstants.RESTAURANT_NOT_FOUND_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleCategoryNotFound(CategoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                ExceptionConstants.CATEGORY_NOT_FOUND_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedUser(UnauthorizedUserException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(
                ExceptionConstants.UNAUTHORIZED_USER_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(DishNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleDishNotFound(DishNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(
                ExceptionConstants.DISH_NOT_FOUND_MESSAGE, LocalDateTime.now()));
    }
}
