package com.plazoleta.plazoleta.plazoleta.domain.ports.out;

import com.plazoleta.plazoleta.plazoleta.domain.model.UserModel;

import java.util.Optional;

public interface UserValidationPort {
    Optional<UserModel> getUserById(Long userId);
}