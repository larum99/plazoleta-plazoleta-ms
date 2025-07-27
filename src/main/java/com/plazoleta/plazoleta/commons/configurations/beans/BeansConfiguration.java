package com.plazoleta.plazoleta.commons.configurations.beans;

import com.plazoleta.plazoleta.plazoleta.domain.ports.out.RestaurantPersistencePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.UserValidationPort;
import com.plazoleta.plazoleta.plazoleta.domain.usecases.RestaurantUseCase;
import com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.feign.UserValidationAdapter;
import com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.persistence.RestaurantPersistenceAdapter;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.UserFeignClient;
import com.plazoleta.plazoleta.plazoleta.infrastructure.mappers.RestaurantEntityMapper;
import com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeansConfiguration {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantEntityMapper restaurantEntityMapper;
    private final UserFeignClient userFeignClient;

    @Bean
    public RestaurantUseCase restaurantUseCase() {
        return new RestaurantUseCase(
                restaurantPersistencePort(),
                userValidationPort()
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
    public UserValidationPort userValidationPort() {
        return new UserValidationAdapter(userFeignClient);
    }
}
