package com.plazoleta.plazoleta.plazoleta.domain.model;

import java.math.BigDecimal;

public class DishModel {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Boolean active;
    private CategoryModel category;
    private RestaurantModel restaurant;

    public DishModel() {
    }

    public DishModel(Long id, String name, String description, BigDecimal price, String imageUrl,
                     Boolean active, CategoryModel category, RestaurantModel restaurant) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.active = active;
        this.category = category;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public CategoryModel getCategory() {
        return category;
    }

    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public RestaurantModel getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantModel restaurant) {
        this.restaurant = restaurant;
    }
}
