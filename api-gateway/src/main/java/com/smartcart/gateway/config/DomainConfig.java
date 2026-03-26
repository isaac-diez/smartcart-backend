package com.smartcart.gateway.config;

import com.smartcart.gateway.config.jwt.JwtAdapter;
import com.smartcart.gateway.config.jwt.JwtPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public JwtPort jwtPort(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration-ms}") long accessExpirationMs,
            @Value("${jwt.refresh-expiration-ms}") long refreshExpirationMs) {
        return new JwtAdapter(secret, accessExpirationMs, refreshExpirationMs);
    }
}