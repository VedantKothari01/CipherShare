package com.ciphershare.file.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "files")
public class File {
    @Id
    @Column(name = "fileID", nullable = false, updatable = false)
    private String fileID = UUID.randomUUID().toString();

    private String fileName;
    private String fileType;
    private Long fileSize;
    private String encryptedPath;
    private String description;
    private String ownerID; // Reference to user-service userID

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
