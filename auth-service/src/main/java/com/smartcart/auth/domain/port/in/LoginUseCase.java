package com.smartcart.auth.domain.port.in;

/**
 * Puerto de entrada: autenticación de usuario.
 * Devuelve un par de tokens (access + refresh).
 */
public interface LoginUseCase {

    record TokenPair(String accessToken, String refreshToken) {}

    TokenPair login(String email, String rawPassword);
    TokenPair refresh(String refreshToken);
}
