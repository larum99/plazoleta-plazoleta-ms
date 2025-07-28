package com.plazoleta.plazoleta.commons.configurations.beans;

import com.plazoleta.plazoleta.plazoleta.domain.ports.in.DishServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.CategoryPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.DishPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.UserValidationPort;
import com.plazoleta.plazoleta.plazoleta.domain.usecases.DishUseCase;
import com.plazoleta.plazoleta.plazoleta.domain.usecases.RestaurantUseCase;
import com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.feign.UserValidationAdapter;
import com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.persistence.CategoryPersistenceAdapter;
import com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.persistence.DishPersistenceAdapter;
import com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.persistence.RestaurantPersistenceAdapter;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.UserFeignClient;
import com.plazoleta.plazoleta.plazoleta.infrastructure.mappers.CategoryEntityMapper;
import com.plazoleta.plazoleta.plazoleta.infrastructure.mappers.DishEntityMapper;
import com.plazoleta.plazoleta.plazoleta.infrastructure.mappers.RestaurantEntityMapper;
import com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql.CategoryRepository;
import com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql.DishRepository;
import com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeansConfiguration {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;

    private final RestaurantEntityMapper restaurantEntityMapper;
    private final DishEntityMapper dishEntityMapper;
    private final CategoryEntityMapper categoryEntityMapper;

    private final UserFeignClient userFeignClient;

    @Bean
    public RestaurantUseCase restaurantUseCase() {
        return new RestaurantUseCase(
                restaurantPersistencePort(),
                userValidationPort()
        );
    }

    @Bean
    public DishServicePort dishServicePort() {
        return new DishUseCase(
                dishPersistencePort(),
                restaurantPersistencePort(),
                categoryPersistencePort()
        );
    }

    @Bean
    public RestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantPersistenceAdapter(
                restaurantRepository,
                restaurantEntityMapper
        );
    }

    @Bean
    public DishPersistencePort dishPersistencePort() {
        return new DishPersistenceAdapter(
                dishRepository,
                dishEntityMapper
        );
    }

    @Bean
    public CategoryPersistencePort categoryPersistencePort() {
        return new CategoryPersistenceAdapter(
                categoryRepository,
                categoryEntityMapper
        );
    }

    @Bean
    public UserValidationPort userValidationPort() {
        return new UserValidationAdapter(userFeignClient);
    }
}
