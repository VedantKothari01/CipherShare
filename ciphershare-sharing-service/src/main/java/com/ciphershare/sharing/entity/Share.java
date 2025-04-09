package com.ciphershare.sharing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "shares")
public class Share {
    @Id
    @Column(name = "share_id", nullable = false, updatable = false)
    private String shareId = UUID.randomUUID().toString();

    @Column(name = "file_id", nullable = false)
    private String fileId;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Column(name = "shared_with_id", nullable = false)
    private String sharedWithId;

    @Column(name = "permission", nullable = false)
    @Enumerated(EnumType.STRING)
    private Permission permission;

    @Column(name = "access_token", nullable = false, unique = true)
    private String accessToken;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    public enum Permission {
        READ,
        WRITE,
        ADMIN
    }

    // Explicit getter and setter for isActive to fix Lombok issue
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
} 