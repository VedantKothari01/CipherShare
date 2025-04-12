package com.ciphershare.sharing.repository;

import com.ciphershare.sharing.entity.SharedFile;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SharedFileRepository extends JpaRepository<SharedFile, String> {
    List<SharedFile> findBySharedWithUserID(String userId);
}