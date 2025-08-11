package com.plazoleta.plazoleta.commons.configurations.beans;

import com.plazoleta.plazoleta.plazoleta.domain.ports.in.DishServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.EmployeeRestaurantServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.OrderServicePort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.in.RoleValidatorPort;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.*;
import com.plazoleta.plazoleta.plazoleta.domain.usecases.DishUseCase;
import com.plazoleta.plazoleta.plazoleta.domain.usecases.EmployeeRestaurantUseCase;
import com.plazoleta.plazoleta.plazoleta.domain.usecases.OrderUseCase;
import com.plazoleta.plazoleta.plazoleta.domain.usecases.RestaurantUseCase;
import com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.feign.OrderTraceabilityAdapter;
import com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.feign.UserValidationAdapter;
import com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.feign.OrderNotificationAdapter;
import com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.persistence.*;
import com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.security.RoleValidatorAdapter;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.MessagingFeignClient;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.UserFeignClient;
import com.plazoleta.plazoleta.plazoleta.infrastructure.mappers.*;
import com.plazoleta.plazoleta.plazoleta.infrastructure.repositories.mysql.*;
import com.plazoleta.plazoleta.plazoleta.infrastructure.security.utils.JwtUtil;
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

    private final OrderRepository orderRepository;
    private final OrderEntityMapper orderEntityMapper;

    private final MessagingFeignClient messagingFeignClient;
    private final UserFeignClient userFeignClient;

    private final EmployeeRestaurantRepository employeeRestaurantRepository;
    private final EmployeeRestaurantEntityMapper employeeRestaurantEntityMapper;

    private final OrderTraceabilityAdapter orderTraceabilityAdapter;

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

    @Bean
    public RoleValidatorPort roleValidatorPort(JwtUtil jwtUtil) {
        return new RoleValidatorAdapter(jwtUtil);
    }

    @Bean
    public OrderServicePort orderServicePort() {
        return new OrderUseCase(
                orderPersistencePort(),
                dishPersistencePort(),
                restaurantPersistencePort(),
                employeeRestaurantPersistencePort(),
                orderNotificationPort(),
                orderTraceabilityPort()
        );
    }

    @Bean
    public OrderPersistencePort orderPersistencePort() {
        return new OrderPersistenceAdapter(
                orderRepository,
                orderEntityMapper
        );
    }

    @Bean
    public EmployeeRestaurantPersistencePort employeeRestaurantPersistencePort() {
        return new EmployeeRestaurantPersistenceAdapter(
                employeeRestaurantRepository
        );
    }

    @Bean
    public EmployeeRestaurantServicePort employeeRestaurantServicePort() {
        return new EmployeeRestaurantUseCase(
                employeeRestaurantPersistencePort(),
                userValidationPort()
        );
    }

    @Bean
    public OrderNotificationPort orderNotificationPort() {
        return new OrderNotificationAdapter(messagingFeignClient, userFeignClient);
    }

    @Bean
    public OrderTraceabilityPort orderTraceabilityPort() {
        return orderTraceabilityAdapter;
    }
}
