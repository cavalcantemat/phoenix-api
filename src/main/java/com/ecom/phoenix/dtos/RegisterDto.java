package com.ecom.phoenix.dtos;

import com.ecom.phoenix.utils.UserRole;

public record RegisterDto(String login, String name, String email, String password, UserRole role) {
}
