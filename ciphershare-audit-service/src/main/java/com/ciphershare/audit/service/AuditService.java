package com.ciphershare.audit.service;

import com.ciphershare.audit.entity.BlockchainRecord;
import com.ciphershare.audit.repository.BlockchainRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuditService {
    private BlockchainRecordRepository recordRepository;

    public BlockchainRecord addRecord(BlockchainRecord record) {
        return recordRepository.save(record);
    }

    public List<BlockchainRecord> getRecordsByFileId(String fileID) {
        return recordRepository.findByFileID(fileID);
    }
}
