package com.plazoleta.plazoleta.plazoleta.domain.usecases;

import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.RestaurantServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.UserValidationPort;
import com.plazoleta.plazoleta.plazoleta.domain.helpers.RestaurantHelper;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;

public class RestaurantUseCase implements RestaurantServicePort {

    private final RestaurantPersistencePort restaurantPersistencePort;
    private final RestaurantHelper helper;

    public RestaurantUseCase(
            RestaurantPersistencePort restaurantPersistencePort,
            UserValidationPort userValidationPort
    ) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.helper = new RestaurantHelper(restaurantPersistencePort, userValidationPort);
    }

    @Override
    public void createRestaurant(RestaurantModel restaurantModel, String role) {
        helper.validateRole(role);
        helper.normalizeFields(restaurantModel);
        helper.validateMandatoryFields(restaurantModel);
        helper.validateNit(restaurantModel.getNit());
        helper.checkIfNitAlreadyExists(restaurantModel.getNit());
        helper.validatePhone(restaurantModel.getPhone());
        helper.validateName(restaurantModel.getName());
        helper.validateLogoUrl(restaurantModel.getLogoUrl());
        helper.validateOwnerExistsAndHasRole(restaurantModel.getOwnerId());

        restaurantPersistencePort.saveRestaurant(restaurantModel);
    }

    @Override
    public PageResult<RestaurantModel> listRestaurants(int page, int size) {
        helper.validatePageNumber(page);
        helper.validatePageSize(size);
        return restaurantPersistencePort.listRestaurantsOrderedByName(page, size);
    }

    @Override
    public boolean existsById(Long restaurantId) {
        return restaurantPersistencePort.existsById(restaurantId);
    }

}
