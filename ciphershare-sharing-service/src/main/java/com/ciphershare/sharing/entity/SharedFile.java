package com.ciphershare.sharing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "sharedFiles")
public class SharedFile {
    @Id
    @Column(name = "shareID", nullable = false, updatable = false)
    private String shareID = UUID.randomUUID().toString();

    private String fileID;
    private String sharedWithUserID;
    private String permissions;
    private String status;
    private LocalDateTime accessExpiry;

    @CreationTimestamp
    private LocalDateTime createdAt;

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
