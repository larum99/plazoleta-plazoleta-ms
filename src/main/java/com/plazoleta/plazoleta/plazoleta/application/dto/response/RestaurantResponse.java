package com.plazoleta.plazoleta.plazoleta.application.dto.response;

public record RestaurantResponse(
        Long id,
        String name,
        String nit,
        String address,
        String phone,
        String logoUrl,
        Long ownerId
) {}
