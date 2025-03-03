package com.ciphershare.audit.controller;

import com.ciphershare.audit.entity.BlockchainRecord;
import com.ciphershare.audit.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/audit")
public class AuditController {
    @Autowired
    private AuditService auditService;

    @PostMapping("/record")
    public ResponseEntity<BlockchainRecord> addRecord(@RequestBody BlockchainRecord record) {
        return ResponseEntity.ok(auditService.addRecord(record));
    }

    @GetMapping("/records/{fileId}")
    public ResponseEntity<List<BlockchainRecord>> getRecords(@PathVariable String fileId) {
        return ResponseEntity.ok(auditService.getRecordsByFileId(fileId));
    }
}
