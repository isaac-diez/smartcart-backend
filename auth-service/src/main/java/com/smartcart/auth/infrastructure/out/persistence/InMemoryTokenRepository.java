package com.smartcart.auth.infrastructure.out.persistence;

import com.smartcart.auth.domain.port.out.TokenRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementación en memoria para MVP.
 * TODO: sustituir por RedisTokenRepository sin cambiar nada del dominio.
 */
@Component
public class InMemoryTokenRepository implements TokenRepositoryPort {

    // token -> userId
    private final Map<String, UUID> tokenStore = new ConcurrentHashMap<>();

    @Override
    public void saveRefreshToken(UUID userId, String token) {
        tokenStore.put(token, userId);
    }

    @Override
    public Optional<UUID> validateRefreshToken(String token) {
        return Optional.ofNullable(tokenStore.get(token));
    }

    @Override
    public void revokeRefreshToken(String token) {
        tokenStore.remove(token);
    }

    @Override
    public void revokeAllUserTokens(UUID userId) {
        tokenStore.entrySet().removeIf(e -> e.getValue().equals(userId));
    }
}
