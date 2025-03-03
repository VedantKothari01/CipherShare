package com.ciphershare.sharing.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "sharedFiles")
public class SharedFile {
    @Id
    @Column(name = "shareID", nullable = false, updatable = false)
    private String shareID = UUID.randomUUID().toString();

    private String fileID;           // Reference to file-service fileID
    private String sharedWithUserID;   // Reference to user-service userID

    private String permissions;        // e.g., "READ", "WRITE"
    private String status;             // e.g., "ACTIVE", "EXPIRED"
    private LocalDateTime accessExpiry;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
