package com.smartcart.auth.domain.port.out;

import java.util.UUID;

/**
 * Puerto de salida: generación y validación de JWT.
 */
public interface JwtPort {
    String generateAccessToken(UUID userId, String username, String email);
    String generateRefreshToken(UUID userId);
    UUID extractUserId(String token);
    boolean isValid(String token);
}
