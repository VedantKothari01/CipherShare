package com.ciphershare.file.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "files")
public class File {

    @Id
    @Column(name = "fileID", nullable = false, updatable = false)
    private String fileID = UUID.randomUUID().toString();
    @Column(name = "file_name", nullable = false)
    private String fileName;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "file_size")
    private Long fileSize;
    @Column(name = "ipfs_cid")
    private String ipfsCid;
    private String ownerID;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "pinata_file_id", nullable = false)
    private String pinataFileId;

    @Column(name = "encrypted", nullable = false)
    private boolean encrypted = false;

    @Column(name = "user_id", nullable = false)
    private String userId;

    public String getPinataFileId() { return pinataFileId; }
    public void setPinataFileId(String pinataFileId) { this.pinataFileId = pinataFileId; }

    public boolean isEncrypted() { return encrypted; }
    public void setEncrypted(boolean encrypted) { this.encrypted = encrypted; }

    public String getFileID() { return fileID; }
    public void setFileID(String fileID) { this.fileID = fileID; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getFileType() { return fileType; }
    public void setFileType(String fileType) { this.fileType = fileType; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public String getIpfsCid() { return ipfsCid; }
    public void setIpfsCid(String ipfsCid) { this.ipfsCid = ipfsCid; }

    public String getOwnerID() { return ownerID; }
    public void setOwnerID(String ownerID) { this.ownerID = ownerID; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
