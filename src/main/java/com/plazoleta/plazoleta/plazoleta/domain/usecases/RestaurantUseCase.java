package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.exceptions.*;
import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.model.UserModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.RestaurantServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.UserValidationPort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.DomainConstants;
import com.plazoleta.plazoleta.plazoleta.domain.utils.RegexUtils;

public class RestaurantUseCase implements RestaurantServicePort {

    private final RestaurantPersistencePort restaurantPersistencePort;
    private final UserValidationPort userValidationPort;

    public RestaurantUseCase(RestaurantPersistencePort restaurantPersistencePort, UserValidationPort userValidationPort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userValidationPort = userValidationPort;
    }

    @Override
    public void createRestaurant(RestaurantModel restaurantModel) {
        normalizeFields(restaurantModel);

        validateMandatoryFields(restaurantModel);
        validateNit(restaurantModel.getNit());
        checkIfNitAlreadyExists(restaurantModel.getNit());
        validatePhone(restaurantModel.getPhone());
        validateName(restaurantModel.getName());
        validateLogoUrl(restaurantModel.getLogoUrl());

        validateOwnerExistsAndHasRole(restaurantModel.getOwnerId());

        restaurantPersistencePort.saveRestaurant(restaurantModel);
    }

    private void normalizeFields(RestaurantModel model) {
        model.setName(safeTrim(model.getName()));
        model.setNit(safeTrim(model.getNit()));
        model.setAddress(safeTrim(model.getAddress()));
        model.setPhone(safeTrim(model.getPhone()));
        model.setLogoUrl(safeTrim(model.getLogoUrl()));
    }

    private String safeTrim(String value) {
        return value == null ? null : value.trim();
    }

    private void validateMandatoryFields(RestaurantModel model) {
        if (isBlank(model.getName())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_RESTAURANT_NAME);
        if (isBlank(model.getNit())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_NIT);
        if (isBlank(model.getAddress())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_ADDRESS);
        if (isBlank(model.getPhone())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_PHONE);
        if (isBlank(model.getLogoUrl())) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_LOGO_URL);
        if (model.getOwnerId() == null) throw new MissingFieldException(DomainConstants.ERROR_REQUIRED_OWNER_ID);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void validateNit(String nit) {
        if (!RegexUtils.DOCUMENT_PATTERN.matcher(nit).matches()) {
            throw new InvalidNitException();
        }
    }

    private void checkIfNitAlreadyExists(String nit) {
        RestaurantModel existing = restaurantPersistencePort.getRestaurantByNit(nit);
        if (existing != null) {
            throw new DuplicateNitException();
        }
    }

    private void validatePhone(String phone) {
        if (phone.length() > DomainConstants.PHONE_MAX_LENGTH ||
                !RegexUtils.PHONE_PATTERN.matcher(phone).matches()) {
            throw new InvalidPhoneException();
        }
    }

    private void validateName(String name) {
        if (!RegexUtils.RESTAURANT_NAME_PATTERN.matcher(name).matches()) {
            throw new InvalidRestaurantNameException();
        }
    }

    private void validateLogoUrl(String logoUrl) {
        if (!RegexUtils.URL_PATTERN.matcher(logoUrl).matches()) {
            throw new InvalidLogoUrlException();
        }
    }

    private void validateOwnerExistsAndHasRole(Long ownerId) {
        UserModel user = userValidationPort.getUserById(ownerId)
                .orElseThrow(UserNotFoundException::new);

        if (!DomainConstants.OWNER.equalsIgnoreCase(user.getRole())) {
            throw new InvalidOwnerException();
        }
    }
}
