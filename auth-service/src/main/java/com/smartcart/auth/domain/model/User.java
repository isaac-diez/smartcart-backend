package com.smartcart.auth.domain.model;

import java.time.Instant;
import java.util.UUID;

/**
 * Entidad de dominio pura. Sin anotaciones de frameworks.
 * Representa un usuario del sistema.
 */
public class User {

    private UUID id;
    private String username;
    private String email;
    private String passwordHash;
    private Instant createdAt;
    private boolean active;

    // Constructor para nuevo usuario
    public static User create(String username, String email, String passwordHash) {
        User user = new User();
        user.id = UUID.randomUUID();
        user.username = username;
        user.email = email;
        user.passwordHash = passwordHash;
        user.createdAt = Instant.now();
        user.active = true;
        return user;
    }

    // Getters
    public UUID getId()             { return id; }
    public String getUsername()     { return username; }
    public String getEmail()        { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Instant getCreatedAt()   { return createdAt; }
    public boolean isActive()       { return active; }

    // Setters necesarios para reconstrucción desde BD
    public void setId(UUID id)                   { this.id = id; }
    public void setUsername(String username)      { this.username = username; }
    public void setEmail(String email)            { this.email = email; }
    public void setPasswordHash(String hash)      { this.passwordHash = hash; }
    public void setCreatedAt(Instant createdAt)   { this.createdAt = createdAt; }
    public void setActive(boolean active)         { this.active = active; }
}
