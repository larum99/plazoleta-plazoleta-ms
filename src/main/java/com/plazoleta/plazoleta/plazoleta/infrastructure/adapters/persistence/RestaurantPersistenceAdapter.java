package com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.persistence;

import com.plazoleta.plazoleta.plazoleta.domain.model.RestaurantModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.RestaurantEntity;
import com.plazoleta.plazoleta.plazoleta.infrastructure.mappers.RestaurantEntityMapper;
import com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class RestaurantPersistenceAdapter implements RestaurantPersistencePort {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;

    @Override
    public void saveRestaurant(RestaurantModel restaurantModel) {
        RestaurantEntity entity = restaurantEntityMapper.modelToEntity(restaurantModel);
        restaurantRepository.save(entity);
    }

    @Override
    public RestaurantModel getRestaurantByNit(String nit) {
        Optional<RestaurantEntity> entity = restaurantRepository.findByNit(nit);
        return restaurantEntityMapper.optionalEntityToModel(entity);
    }

    @Override
    public RestaurantModel getRestaurantByName(String name) {
        Optional<RestaurantEntity> entity = restaurantRepository.findByName(name);
        return restaurantEntityMapper.optionalEntityToModel(entity);
    }

    @Override
    public Optional<RestaurantModel> getRestaurantById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantEntityMapper::entityToModel);
    }
}
