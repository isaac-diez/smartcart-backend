package com.smartcart.gateway.config;

import com.smartcart.gateway.config.jwt.JwtPort;
import org.slf4j.Logger;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

    private final JwtPort jwt;

    // Rutas que no requieren token
    private static final List<String> PUBLIC_PATHS = List.of(
            "/api/auth/register",
            "/api/auth/login",
            "/api/auth/refresh",
            "/actuator/health"
    );

    public JwtAuthFilter(JwtPort jwt) {
        this.jwt = jwt;
        System.out.println(">>> JwtAuthFilter CREADO <<<");
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Logger log = org.slf4j.LoggerFactory.getLogger(JwtAuthFilter.class);
        String path = exchange.getRequest().getURI().getPath();
        log.debug("JwtAuthFilter - path: {}", path);

        if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
            log.debug("JwtAuthFilter - ruta pública, sin validación");
            return chain.filter(exchange);
        }

        String header = exchange.getRequest().getHeaders().getFirst("Authorization");
        log.debug("JwtAuthFilter - Authorization header: {}", header);

        if (header == null || !header.startsWith("Bearer ")) {
            log.debug("JwtAuthFilter - header ausente o inválido, devolviendo 401");
            return unauthorized(exchange);
        }

        String token = header.substring(7);
        log.debug("JwtAuthFilter - validando token: {}...", token.substring(0, 20));

        if (!jwt.isValid(token)) {
            log.debug("JwtAuthFilter - token inválido, devolviendo 401");
            return unauthorized(exchange);
        }

        // Token válido → propaga la petición añadiendo el userId como header interno
        UUID userId = jwt.extractUserId(token);
        ServerWebExchange mutated = exchange.mutate()
                .request(r -> r.header("X-User-Id", userId.toString()))
                .build();
        log.debug("JwtAuthFilter - token válido, propagando");
        return chain.filter(mutated);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return -1; // Ejecutar antes que el resto de filtros
    }
}