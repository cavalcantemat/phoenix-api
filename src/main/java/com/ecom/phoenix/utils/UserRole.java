package com.ecom.phoenix.utils;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    USER("user"),
    EMPLOYEE("employee");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
