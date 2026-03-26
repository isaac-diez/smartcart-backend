package com.smartcart.auth.domain.port.out;

import com.smartcart.auth.domain.model.User;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida: contrato de persistencia de usuarios.
 * El dominio declara lo que necesita; la infra lo implementa.
 */
public interface UserRepositoryPort {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
