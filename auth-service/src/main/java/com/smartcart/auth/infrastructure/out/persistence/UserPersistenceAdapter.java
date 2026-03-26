package com.smartcart.auth.infrastructure.out.persistence;

import com.smartcart.auth.domain.model.User;
import com.smartcart.auth.domain.port.out.UserRepositoryPort;
import com.smartcart.auth.infrastructure.out.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador de salida: conecta el dominio con JPA.
 * Implementa el puerto que el dominio declaró.
 */
@Component
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;

    public UserPersistenceAdapter(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public User save(User user) {
        UserEntity entity = toEntity(user);
        UserEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return jpaRepository.findByUsername(username).map(this::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return jpaRepository.existsByUsername(username);
    }

    // ── Mappers privados ──

    private UserEntity toEntity(User user) {
        UserEntity e = new UserEntity();
        e.setId(user.getId());
        e.setUsername(user.getUsername());
        e.setEmail(user.getEmail());
        e.setPasswordHash(user.getPasswordHash());
        e.setCreatedAt(user.getCreatedAt());
        e.setActive(user.isActive());
        return e;
    }

    private User toDomain(UserEntity e) {
        User user = new User();
        user.setId(e.getId());
        user.setUsername(e.getUsername());
        user.setEmail(e.getEmail());
        user.setPasswordHash(e.getPasswordHash());
        user.setCreatedAt(e.getCreatedAt());
        user.setActive(e.isActive());
        return user;
    }
}
