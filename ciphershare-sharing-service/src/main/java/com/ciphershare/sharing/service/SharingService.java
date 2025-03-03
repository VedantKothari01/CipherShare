package com.ciphershare.sharing.service;

import com.ciphershare.sharing.entity.SharedFile;
import com.ciphershare.sharing.repository.SharedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SharingService {
    @Autowired
    private SharedFileRepository sharedFileRepository;

    public SharedFile shareFile(SharedFile sharedFile) {
        return sharedFileRepository.save(sharedFile);
    }

    public List<SharedFile> getSharedFilesForUser(String userId) {
        return sharedFileRepository.findBySharedWithUserID(userId);
    }

    public SharedFile updateShare(String shareID, SharedFile updated) {
        SharedFile existing = sharedFileRepository.findById(shareID)
                .orElseThrow(() -> new RuntimeException("Share record not found"));
        existing.setPermissions(updated.getPermissions());
        existing.setStatus(updated.getStatus());
        existing.setAccessExpiry(updated.getAccessExpiry());
        return sharedFileRepository.save(existing);
    }

    public void revokeShare(String shareID) {
        sharedFileRepository.deleteById(shareID);
    }
}
