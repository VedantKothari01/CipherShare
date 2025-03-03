package com.ciphershare.file.service;

import com.ciphershare.file.client.AuditClient;
import com.ciphershare.file.dto.BlockchainRecordDTO;
import com.ciphershare.file.entity.File;
import com.ciphershare.file.entity.FileVersion;
import com.ciphershare.file.repository.FileRepository;
import com.ciphershare.file.repository.FileVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FileService {
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileVersionRepository fileVersionRepository;

    @Autowired
    private AuditClient auditClient;

    public File createFile(File file) {
        File savedFile = fileRepository.save(file);
        // Create a blockchain record DTO for audit logging
        BlockchainRecordDTO recordDTO = new BlockchainRecordDTO();
        recordDTO.setFileID(savedFile.getFileID());
        recordDTO.setTxnHash("dummyTxnHash"); // Replace with actual txn hash generation in production
        recordDTO.setActionType("FILE_CREATED");
        try {
            auditClient.addRecord(recordDTO);
        } catch (Exception e) {
            System.err.println("Error calling Audit Service: " + e.getMessage());
        }
        return savedFile;
    }

    public File getFile(String fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    public File updateFile(String fileId, File updatedFile) {
        File existing = getFile(fileId);
        existing.setFileName(updatedFile.getFileName());
        existing.setFileType(updatedFile.getFileType());
        existing.setFileSize(updatedFile.getFileSize());
        existing.setEncryptedPath(updatedFile.getEncryptedPath());
        existing.setDescription(updatedFile.getDescription());
        return fileRepository.save(existing);
    }

    public void deleteFile(String fileId) {
        fileRepository.deleteById(fileId);
    }

    // File Versioning Methods
    public FileVersion addFileVersion(FileVersion version) {
        return fileVersionRepository.save(version);
    }

    public List<FileVersion> getFileVersions(String fileId) {
        return fileVersionRepository.findByFileID(fileId);
    }
}
