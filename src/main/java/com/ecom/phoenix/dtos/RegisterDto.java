package com.ecom.phoenix.dtos;

import com.ecom.phoenix.utils.UserRole;

public record RegisterDto(String login, String password, UserRole role) {
}
