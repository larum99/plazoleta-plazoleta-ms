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

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ExceptionResponse> handleForbiddenException(ForbiddenException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(
                ExceptionConstants.FORBIDDEN_OPERATION_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(ClientHasActiveOrderException.class)
    public ResponseEntity<ExceptionResponse> handleClientHasActiveOrder(ClientHasActiveOrderException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ExceptionResponse(ExceptionConstants.CLIENT_HAS_ACTIVE_ORDER_MESSAGE, LocalDateTime.now())

        );
    }

    @ExceptionHandler(DishDoesNotBelongToRestaurantException.class)
    public ResponseEntity<ExceptionResponse> handleDishNotBelongToRestaurant(DishDoesNotBelongToRestaurantException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionResponse(ExceptionConstants.DISH_NOT_BELONG_RESTAURANT_MESSAGE, LocalDateTime.now())
        );
    }

    @ExceptionHandler(EmployeeAlreadyAssignedException.class)
    public ResponseEntity<ExceptionResponse> handleEmployeeAlreadyAssigned(EmployeeAlreadyAssignedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(
                ExceptionConstants.EMPLOYEE_ALREADY_ASSIGNED_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidEmployeeException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidEmployee(InvalidEmployeeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                ExceptionConstants.INVALID_EMPLOYEE_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidRestaurantAssignmentException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidRestaurantAssignment(InvalidRestaurantAssignmentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                ExceptionConstants.INVALID_RESTAURANT_ASSIGNMENT_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(OrderAlreadyAssignedException.class)
    public ResponseEntity<ExceptionResponse> handleOrderAlreadyAssigned(OrderAlreadyAssignedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ExceptionResponse(
                ExceptionConstants.ORDER_ALREADY_ASSIGNED_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(InvalidOrderStatusException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidOrderStatus(InvalidOrderStatusException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                ExceptionConstants.INVALID_ORDER_STATUS_MESSAGE, LocalDateTime.now()));
    }

    @ExceptionHandler(UnauthorizedOrderAccessException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedOrderAccess(UnauthorizedOrderAccessException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionResponse(
                ExceptionConstants.UNAUTHORIZED_ORDER_ACCESS_MESSAGE, LocalDateTime.now()));
    }

}
