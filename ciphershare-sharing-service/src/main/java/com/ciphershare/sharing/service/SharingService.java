package com.ciphershare.sharing.service;

import com.ciphershare.sharing.client.FileServiceClient;
import com.ciphershare.sharing.client.UserServiceClient;
import com.ciphershare.sharing.entity.SharedFile;
import com.ciphershare.sharing.repository.SharedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SharingService {
    @Autowired
    private SharedFileRepository sharedFileRepository;
    
    @Autowired
    private FileServiceClient fileServiceClient;
    
    @Autowired
    private UserServiceClient userServiceClient;

    public SharedFile shareFile(SharedFile sharedFile) {
        // Verify file exists
        fileServiceClient.getFileUrl(sharedFile.getFileID());
        
        // Verify user exists
        if (!userServiceClient.checkUserExists(sharedFile.getSharedWithUserID())) {
            throw new RuntimeException("User does not exist");
        }
        
        return sharedFileRepository.save(sharedFile);
    }

    public List<SharedFile> getSharedFilesForUser(String userId) {
        // Verify user exists
        if (!userServiceClient.checkUserExists(userId)) {
            throw new RuntimeException("User does not exist");
        }
        
        return sharedFileRepository.findBySharedWithUserID(userId);
    }

    public SharedFile updateShare(String shareID, SharedFile updated) {
        SharedFile existing = sharedFileRepository.findById(shareID)
                .orElseThrow(() -> new RuntimeException("Share record not found"));
                
        // Verify user exists if changing shared user
        if (!existing.getSharedWithUserID().equals(updated.getSharedWithUserID())) {
            if (!userServiceClient.checkUserExists(updated.getSharedWithUserID())) {
                throw new RuntimeException("User does not exist");
            }
        }
        
        existing.setPermissions(updated.getPermissions());
        existing.setStatus(updated.getStatus());
        existing.setAccessExpiry(updated.getAccessExpiry());
        existing.setSharedWithUserID(updated.getSharedWithUserID());
        return sharedFileRepository.save(existing);
    }

    public void revokeShare(String shareID) {
        sharedFileRepository.deleteById(shareID);
    }
}
