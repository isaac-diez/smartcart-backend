package com.smartcart.listitem.infrastructure.config;

import com.smartcart.listitem.infrastructure.in.rest.XUserIdFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class
SecurityConfig {

    private final XUserIdFilter xUserIdFilter;

    public SecurityConfig(XUserIdFilter xUserIdFilter) { this.xUserIdFilter = xUserIdFilter; }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated()
            )
                .addFilterBefore(xUserIdFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
