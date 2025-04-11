package com.ciphershare.audit.controller;

import com.ciphershare.audit.entity.BlockchainRecord;
import com.ciphershare.audit.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Audit API", description = "Manages blockchain-style immutable audit logs")
@RestController
@RequestMapping("/api/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @Operation(
        summary = "Log Audit Event", 
        description = "Logs a new audit event with detailed information",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                      "fileId": "file123",
                      "userId": "user123",
                      "action": "DOWNLOAD",
                      "metadata": "Downloaded file: document.pdf",
                      "ipAddress": "192.168.1.1",
                      "userAgent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64)"
                    }
                    """
                )
            )
        )
    )
    @PostMapping("/log")
    public ResponseEntity<BlockchainRecord> logEvent(@RequestBody AuditLogRequest request) {
        BlockchainRecord record = new BlockchainRecord();
        record.setFileId(request.getFileId());
        record.setUserId(request.getUserId());
        record.setAction(request.getAction());
        record.setMetadata(request.getMetadata());
        record.setIpAddress(request.getIpAddress());
        record.setUserAgent(request.getUserAgent());
        return ResponseEntity.ok(auditService.addRecord(record));
    }

    @Operation(
        summary = "Get Audit Records by File ID", 
        description = "Retrieves all audit records for a specific file"
    )
    @GetMapping("/records/file/{fileId}")
    public ResponseEntity<List<BlockchainRecord>> getRecordsByFileId(@PathVariable String fileId) {
        return ResponseEntity.ok(auditService.getRecordsByFileId(fileId));
    }

    @Operation(
        summary = "Get Audit Records by User ID", 
        description = "Retrieves all audit records for a specific user"
    )
    @GetMapping("/records/user/{userId}")
    public ResponseEntity<List<BlockchainRecord>> getRecordsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(auditService.getRecordsByUserId(userId));
    }

    @Operation(
        summary = "Get Recent Audit Records", 
        description = "Retrieves the most recent audit records"
    )
    @GetMapping("/records/recent")
    public ResponseEntity<List<BlockchainRecord>> getRecentRecords(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(auditService.getRecentRecords(limit));
    }
}

// Request DTO for audit logging
class AuditLogRequest {
    private String fileId;
    private String userId;
    private String action;
    private String metadata;
    private String ipAddress;
    private String userAgent;

    // Getters and Setters
    public String getFileId() { return fileId; }
    public void setFileId(String fileId) { this.fileId = fileId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getAction() { return action; }
    public void setAction(String action) { this.action = action; }
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
    public String getIpAddress() { return ipAddress; }
    public void setIpAddress(String ipAddress) { this.ipAddress = ipAddress; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
}
