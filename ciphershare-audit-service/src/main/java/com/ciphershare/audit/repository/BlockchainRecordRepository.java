package com.ciphershare.audit.repository;

import com.ciphershare.audit.entity.BlockchainRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BlockchainRecordRepository extends JpaRepository<BlockchainRecord, String> {
    List<BlockchainRecord> findByFileID(String fileID);
}
