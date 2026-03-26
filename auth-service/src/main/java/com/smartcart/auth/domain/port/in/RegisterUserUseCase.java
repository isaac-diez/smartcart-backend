package com.smartcart.auth.domain.port.in;

import com.smartcart.auth.domain.model.User;

/**
 * Puerto de entrada: define lo que este servicio puede hacer.
 * Implementado por el servicio de dominio.
 * Invocado por el adaptador REST (controller).
 */
public interface RegisterUserUseCase {
    User register(String username, String email, String rawPassword);
}
