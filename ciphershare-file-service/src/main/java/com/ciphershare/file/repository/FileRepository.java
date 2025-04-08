package com.ciphershare.file.repository;

import com.ciphershare.file.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FileRepository extends JpaRepository<File, String> {
    Optional<File> findByFileID(String fileID);  // Use fileID instead of CID
}
