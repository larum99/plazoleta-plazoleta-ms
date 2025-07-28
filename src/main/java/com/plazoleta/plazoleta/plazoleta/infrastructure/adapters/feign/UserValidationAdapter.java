package com.plazoleta.plazoleta.plazoleta.infrastructure.adapters.feign;

import com.plazoleta.plazoleta.plazoleta.domain.utils.UserModel;
import com.plazoleta.plazoleta.plazoleta.domain.ports.out.UserValidationPort;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.UserFeignClient;
import com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto.UserResponse;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserValidationAdapter implements UserValidationPort {

    private final UserFeignClient userFeignClient;

    @Override
    public Optional<UserModel> getUserById(Long userId) {
        try {
            UserResponse response = userFeignClient.getUserById(userId);
            return Optional.of(new UserModel(response.id(), response.role()));
        } catch (FeignException.NotFound e) {
            return Optional.empty();
        }
    }
}
