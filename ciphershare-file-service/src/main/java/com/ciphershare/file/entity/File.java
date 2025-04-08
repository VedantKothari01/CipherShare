package com.ciphershare.file.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
public class File {

    @Id
    @Column(name = "fileID", nullable = false, updatable = false)
    private String fileID;  // Now stores Pinata's file ID

    private String fileName;
    private String fileType;
    private Long fileSize;
    private String ipfsCid;
    private String ownerID;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

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
}
