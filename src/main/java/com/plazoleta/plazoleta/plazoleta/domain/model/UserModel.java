package com.plazoleta.plazoleta.plazoleta.domain.model;

public class UserModel {
    private Long id;
    private String role;

    public UserModel(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
