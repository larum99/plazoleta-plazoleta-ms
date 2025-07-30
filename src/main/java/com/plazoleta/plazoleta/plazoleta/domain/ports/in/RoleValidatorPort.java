package com.plazoleta.plazoleta.plazoleta.domain.ports.in;

public interface RoleValidatorPort {
    String extractRole(String token);
    Long extractUserId(String token);
}
