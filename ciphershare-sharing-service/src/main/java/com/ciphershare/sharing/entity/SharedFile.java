package com.ciphershare.sharing.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "shared_files")
public class SharedFile {
    @Id
    @Column(name = "share_id")
    private String shareID;

    @Column(name = "file_id", nullable = false)
    private String fileID;

    @Column(name = "shared_with_user_id", nullable = false)
    private String sharedWithUserID;

    @Column(name = "permissions")
    private String permissions;

    @Column(name = "status")
    private String status;

    @Column(name = "access_expiry")
    private LocalDateTime accessExpiry;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getAccessExpiry() {
        return accessExpiry;
    }

    public void setAccessExpiry(LocalDateTime accessExpiry) {
        this.accessExpiry = accessExpiry;
    }



}