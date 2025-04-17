package com.ciphershare.audit.config;

import com.ciphershare.audit.entity.BlockchainRecord;
import com.ciphershare.audit.repository.BlockchainRecordRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BlockchainRecordRepository repository;

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            List<BlockchainRecord> testData = List.of(
                    createRecord("fileA123", "userA001", "UPLOAD", "Uploaded fileA.docx"),
                    createRecord("fileA123", "userA001", "SHARE", "Shared with userB002"),
                    createRecord("fileB456", "userB002", "DOWNLOAD", "Downloaded fileB.pdf"),
                    createRecord("fileC789", "userC003", "UPLOAD", "Uploaded image.png"),
                    createRecord("fileC789", "userC003", "SHARE", "Shared with userA001"),
                    createRecord("fileD321", "userA001", "DELETE", "Deleted confidential.txt")
            );

            repository.saveAll(testData);
            System.out.println("Test audit data initialized.");
        } else {
            System.out.println("Test data already present. Skipping initialization.");
        }
    }

    private BlockchainRecord createRecord(String fileId, String userId, String action, String metadata) {
        BlockchainRecord record = new BlockchainRecord();
        record.setRecordId(UUID.randomUUID().toString());
        record.setFileId(fileId);
        record.setUserId(userId);
        record.setAction(action);
        record.setMetadata(metadata);
        record.setIpAddress("192.168.0.10");
        record.setUserAgent("SwaggerTestAgent/1.0");
        return record;
    }
}
