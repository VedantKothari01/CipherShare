package com.ciphershare.file.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "fileVersions")
public class FileVersion {
    @Id
    @Column(name = "versionID", nullable = false, updatable = false)
    private String versionID = UUID.randomUUID().toString();

    private String fileID; // foreign key to File
    private int versionNumber;

    @CreationTimestamp
    private LocalDateTime timestamp;

    private String changeLog;
}
