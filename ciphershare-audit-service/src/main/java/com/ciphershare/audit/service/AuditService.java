package com.ciphershare.audit.service;

import com.ciphershare.audit.entity.BlockchainRecord;
import com.ciphershare.audit.repository.BlockchainRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {

    @Autowired
    private BlockchainRecordRepository repository;

    public BlockchainRecord addRecord(BlockchainRecord record) {
        // Generate a transaction hash (simplified for now)
        record.setTxnHash(generateTxnHash(record));
        return repository.save(record);
    }

    public List<BlockchainRecord> getRecordsByFileId(String fileId) {
        return repository.findByFileIdOrderByTimestampDesc(fileId);
    }

    public List<BlockchainRecord> getRecordsByUserId(String userId) {
        return repository.findByUserIdOrderByTimestampDesc(userId);
    }

    public List<BlockchainRecord> getRecentRecords(int limit) {
        return repository.findAll(
            PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "timestamp"))
        ).getContent();
    }

    private String generateTxnHash(BlockchainRecord record) {

        String data = record.getFileId() + 
                     record.getUserId() + 
                     record.getAction() + 
                     record.getTimestamp().toString();
        return Integer.toHexString(data.hashCode());
    }
}
