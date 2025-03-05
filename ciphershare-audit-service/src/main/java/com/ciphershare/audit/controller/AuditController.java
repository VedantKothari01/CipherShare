package com.ciphershare.audit.controller;

import com.ciphershare.audit.entity.BlockchainRecord;
import com.ciphershare.audit.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Add Audit Record", description = "Adds a new audit record to the blockchain log.")
    @PostMapping("/record")
    public ResponseEntity<BlockchainRecord> addRecord(@RequestBody BlockchainRecord record) {
        return ResponseEntity.ok(auditService.addRecord(record));
    }

    @Operation(summary = "Get Audit Records by File ID", description = "Retrieves all audit records for a specific file.")
    @GetMapping("/records/{fileId}")
    public ResponseEntity<List<BlockchainRecord>> getRecords(@PathVariable String fileId) {
        return ResponseEntity.ok(auditService.getRecordsByFileId(fileId));
    }
}
