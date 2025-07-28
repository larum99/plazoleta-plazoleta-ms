package com.plazoleta.plazoleta.plazoleta.infrastructure.mappers;

import com.plazoleta.plazoleta.plazoleta.domain.model.CategoryModel;
import com.plazoleta.plazoleta.plazoleta.infrastructure.entities.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface CategoryEntityMapper {

    CategoryModel entityToModel(CategoryEntity entity);
    CategoryEntity modelToEntity(CategoryModel model);

    default CategoryModel optionalEntityToModel(Optional<CategoryEntity> optionalEntity) {
        if (optionalEntity.isPresent()) {
            CategoryEntity entity = optionalEntity.get();
            return entityToModel(entity);
        }
        return null;
    }
}
