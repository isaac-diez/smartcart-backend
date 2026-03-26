package com.smartcart.auth.domain.service;

import com.smartcart.auth.domain.model.User;
import com.smartcart.auth.domain.port.in.LoginUseCase;
import com.smartcart.auth.domain.port.in.RegisterUserUseCase;
import com.smartcart.auth.domain.port.out.JwtPort;
import com.smartcart.auth.domain.port.out.PasswordEncoderPort;
import com.smartcart.auth.domain.port.out.TokenRepositoryPort;
import com.smartcart.auth.domain.port.out.UserRepositoryPort;

/**
 * Implementación de los casos de uso de autenticación.
 *
 * IMPORTANTE: esta clase NO tiene anotaciones de Spring.
 * Spring la gestionará desde la capa de infraestructura
 * mediante @Bean en una clase @Configuration.
 */
public class AuthService implements RegisterUserUseCase, LoginUseCase {

    private final UserRepositoryPort userRepository;
    private final TokenRepositoryPort tokenRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final JwtPort jwt;

    public AuthService(UserRepositoryPort userRepository,
                       TokenRepositoryPort tokenRepository,
                       PasswordEncoderPort passwordEncoder,
                       JwtPort jwt) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwt = jwt;
    }

    @Override
    public User register(String username, String email, String rawPassword) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email ya registrado: " + email);
        }
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username ya en uso: " + username);
        }
        String hash = passwordEncoder.encode(rawPassword);
        User user = User.create(username, email, hash);
        return userRepository.save(user);
    }

    @Override
    public TokenPair login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciales inválidas"));

        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Credenciales inválidas");
        }
        return generateTokenPair(user);
    }

    @Override
    public TokenPair refresh(String refreshToken) {
        var userId = tokenRepository.validateRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("Refresh token inválido o expirado"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado: " + userId));

        tokenRepository.revokeRefreshToken(refreshToken);
        return generateTokenPair(user);
    }

    private TokenPair generateTokenPair(User user) {
        String access  = jwt.generateAccessToken(user.getId(), user.getUsername(), user.getEmail());
        String refresh = jwt.generateRefreshToken(user.getId());
        tokenRepository.saveRefreshToken(user.getId(), refresh);
        return new TokenPair(access, refresh);
    }
}
