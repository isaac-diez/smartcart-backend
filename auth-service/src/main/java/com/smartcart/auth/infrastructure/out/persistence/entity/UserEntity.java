package com.smartcart.auth.infrastructure.out.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

/**
 * Entidad JPA. Solo vive en la capa de infraestructura.
 * No es el User del dominio — hay un mapper entre ambas.
 */
@Entity
@Table(name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "username")
    })
public class UserEntity {

    @Id
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private boolean active;

    // Getters & Setters
    public UUID getId()               { return id; }
    public void setId(UUID id)        { this.id = id; }
    public String getUsername()       { return username; }
    public void setUsername(String u) { this.username = u; }
    public String getEmail()          { return email; }
    public void setEmail(String e)    { this.email = e; }
    public String getPasswordHash()   { return passwordHash; }
    public void setPasswordHash(String h) { this.passwordHash = h; }
    public Instant getCreatedAt()     { return createdAt; }
    public void setCreatedAt(Instant t) { this.createdAt = t; }
    public boolean isActive()         { return active; }
    public void setActive(boolean a)  { this.active = a; }
}
