package com.plazoleta.plazoleta.plazoleta.domain.model;

public class RestaurantSummaryModel {
    private String name;
    private String logoUrl;

    public RestaurantSummaryModel(String name, String logoUrl) {
        this.name = name;
        this.logoUrl = logoUrl;
    }

    public String getName() {
        return name;
    }

    public String getLogoUrl() {
        return logoUrl;
    }
}