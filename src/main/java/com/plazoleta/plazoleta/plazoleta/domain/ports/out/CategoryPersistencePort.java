package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import com.plazoleta.plazoleta.plazoleta.domain.model.CategoryModel;

import java.util.Optional;

public interface CategoryPersistencePort {
    Optional<CategoryModel> getCategoryById(Long id);
}
