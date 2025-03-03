package com.ciphershare.file.client;

import com.ciphershare.file.dto.BlockchainRecordDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "audit-service", url = "http://localhost:8084")
public interface AuditClient {
    @PostMapping("/api/audit/record")
    BlockchainRecordDTO addRecord(BlockchainRecordDTO recordDTO);
}
