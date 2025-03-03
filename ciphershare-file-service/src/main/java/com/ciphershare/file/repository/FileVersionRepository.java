package com.ciphershare.file.repository;

import com.ciphershare.file.entity.FileVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FileVersionRepository extends JpaRepository<FileVersion, String> {
    List<FileVersion> findByFileID(String fileID);
}
