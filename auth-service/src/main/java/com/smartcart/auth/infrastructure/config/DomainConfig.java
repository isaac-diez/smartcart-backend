package com.smartcart.auth.infrastructure.config;

import com.smartcart.auth.domain.port.out.JwtPort;
import com.smartcart.auth.domain.port.out.PasswordEncoderPort;
import com.smartcart.auth.domain.port.out.TokenRepositoryPort;
import com.smartcart.auth.domain.port.out.UserRepositoryPort;
import com.smartcart.auth.domain.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Aquí Spring aprende cómo construir el dominio.
 * El dominio nunca sabe que Spring existe.
 */
@Configuration
public class DomainConfig {

    @Bean
    public AuthService authService(
            UserRepositoryPort userRepository,
            TokenRepositoryPort tokenRepository,
            PasswordEncoderPort passwordEncoder,
            JwtPort jwt) {
        return new AuthService(userRepository, tokenRepository, passwordEncoder, jwt);
    }
}
