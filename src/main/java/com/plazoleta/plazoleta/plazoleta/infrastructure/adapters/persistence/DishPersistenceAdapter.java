package com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.persistence;

import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.DishEntity;
import com.plazoleta.plazoleta.plazoleta.infrastructure.mappers.DishEntityMapper;
import com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DishPersistenceAdapter implements DishPersistencePort {

    private final DishRepository dishRepository;
    private final DishEntityMapper dishEntityMapper;

    @Override
    public void saveDish(DishModel dishModel) {
        DishEntity entity = dishEntityMapper.modelToEntity(dishModel);
        dishRepository.save(entity);
    }

    @Override
    public Optional<DishModel> getDishById(Long id) {
        Optional<DishEntity> optionalEntity = dishRepository.findById(id);
        if (optionalEntity.isPresent()) {
            DishEntity entity = optionalEntity.get();
            DishModel model = dishEntityMapper.entityToModel(entity);
            return Optional.of(model);
        }
        return Optional.empty();
    }


}
