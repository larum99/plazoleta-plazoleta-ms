package com.plazoleta.plazoleta.plazoleta.application.dto.request;

public record SaveRestaurantRequest(
        String name,
        String nit,
        String address,
        String phone,
        String logoUrl,
        Long ownerId
) {}
