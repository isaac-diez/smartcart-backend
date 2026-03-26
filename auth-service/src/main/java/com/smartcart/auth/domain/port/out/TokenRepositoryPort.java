package com.smartcart.auth.domain.port.out;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida: almacenamiento/validación de refresh tokens.
 * Podría ser Redis, BD, o ambos.
 */
public interface TokenRepositoryPort {
    void saveRefreshToken(UUID userId, String token);
    Optional<UUID> validateRefreshToken(String token);
    void revokeRefreshToken(String token);
    void revokeAllUserTokens(UUID userId);
}
