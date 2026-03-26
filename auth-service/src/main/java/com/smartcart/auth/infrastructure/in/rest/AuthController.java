package com.smartcart.auth.infrastructure.in.rest;

import com.smartcart.auth.application.dto.*;
import com.smartcart.auth.domain.model.User;
import com.smartcart.auth.domain.port.in.LoginUseCase;
import com.smartcart.auth.domain.port.in.RegisterUserUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final RegisterUserUseCase registerUser;
    private final LoginUseCase login;

    // Inyectamos las interfaces, nunca la implementación concreta
    public AuthController(RegisterUserUseCase registerUser, LoginUseCase login) {
        this.registerUser = registerUser;
        this.login = login;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        User user = registerUser.register(request.username(), request.email(), request.password());
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.from(user));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        var tokens = login.login(request.email(), request.password());
        return ResponseEntity.ok(TokenResponse.of(tokens.accessToken(), tokens.refreshToken(), 86400));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestParam String refreshToken) {
        var tokens = login.refresh(refreshToken);
        return ResponseEntity.ok(TokenResponse.of(tokens.accessToken(), tokens.refreshToken(), 86400));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("auth-service OK");
    }
}
