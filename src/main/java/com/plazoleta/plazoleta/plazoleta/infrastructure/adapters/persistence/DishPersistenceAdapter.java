package com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.persistence;

import com.plazoleta.plazoleta.plazoleta.domain.criteria.DishCriteria;
import com.plazoleta.plazoleta.plazoleta.domain.model.DishModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.utils.PageResult;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.DishEntity;
import com.plazoleta.plazoleta.plazoleta.infrastructure.mappers.DishEntityMapper;
import com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql.DishRepository;
import com.plazoleta.plazoleta.plazoleta.infrastructure.specifications.DishSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public void updateDish(DishModel model) {
        DishEntity entity = dishEntityMapper.modelToEntity(model);
        dishRepository.save(entity);
    }

    @Override
    public PageResult<DishModel> getMenuByCriteria(DishCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.getPage(), criteria.getSize());

        Specification<DishEntity> spec = Specification
                .where(DishSpecification.hasRestaurantId(criteria.getRestaurantId()))
                .and(DishSpecification.hasCategoryId(criteria.getCategoryId()));

        Page<DishEntity> page = dishRepository.findAll(spec, pageable);
        List<DishModel> results = dishEntityMapper.entityListToModelList(page.getContent());


        return new PageResult<>(
                results,
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize(),
                page.isFirst(),
                page.isLast()
        );
    }

}
