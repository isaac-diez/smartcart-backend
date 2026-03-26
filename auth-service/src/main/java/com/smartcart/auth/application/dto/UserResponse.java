package com.smartcart.auth.application.dto;

import com.smartcart.auth.domain.model.User;
import java.util.UUID;

public record UserResponse(UUID id, String username, String email) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }
}
