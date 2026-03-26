package com.smartcart.auth.domain.port.out;

/**
 * Puerto de salida: abstracción del encoder de contraseñas.
 * Así el dominio no depende de BCrypt ni de Spring Security.
 */
public interface PasswordEncoderPort {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
