package com.ciphershare.audit.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "blockchainRecords")
public class BlockchainRecord {
    @Id
    @Column(name = "recordID", nullable = false, updatable = false)
    private String recordID = UUID.randomUUID().toString();

    private String fileID; // Reference to file-service fileID
    private String txnHash;

    @CreationTimestamp
    private LocalDateTime timestamp;

    private String actionType; // e.g., "FILE_CREATED", "FILE_UPDATED"
}
