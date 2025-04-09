package com.ciphershare.audit.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "blockchain_records")
public class BlockchainRecord {
    @Id
    @Column(name = "record_id", nullable = false, updatable = false)
    private String recordId = UUID.randomUUID().toString();

    @Column(name = "file_id", nullable = false)
    private String fileId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "action", nullable = false)
    private String action; 

    @Column(name = "txn_hash")
    private String txnHash;

    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata; 

    @CreationTimestamp
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;
}
