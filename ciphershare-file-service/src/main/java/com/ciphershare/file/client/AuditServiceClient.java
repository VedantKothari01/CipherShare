package com.ciphershare.file.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "audit-service")
public interface AuditServiceClient {
    
    @PostMapping("/api/audit/log")
    void logAuditEvent(@RequestBody Map<String, Object> auditEvent);
} 