package com.plazoleta.plazoleta.plazoleta.infrastructure.clients.feign.dto;

import java.time.LocalDate;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String identityDocument,
        String phoneNumber,
        LocalDate birthDate,
        String email,
        String role
) {}
