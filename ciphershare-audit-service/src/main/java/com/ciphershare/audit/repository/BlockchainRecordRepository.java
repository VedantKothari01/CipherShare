package com.ciphershare.audit.repository;

import com.ciphershare.audit.entity.BlockchainRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockchainRecordRepository extends JpaRepository<BlockchainRecord, String> {
    List<BlockchainRecord> findByFileIdOrderByTimestampDesc(String fileId);
    List<BlockchainRecord> findByUserIdOrderByTimestampDesc(String userId);
}
