package com.ciphershare.sharing.service;

import com.ciphershare.sharing.entity.Share;
import com.ciphershare.sharing.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShareService {

    @Autowired
    private ShareRepository repository;

    @Transactional
    public Share createShare(String fileId, String ownerId, String sharedWithId, Share.Permission permission) {
        // Check if share already exists
        if (repository.existsByFileIdAndSharedWithIdAndIsActiveTrue(fileId, sharedWithId)) {
            throw new IllegalArgumentException("File is already shared with this user");
        }

        Share share = new Share();
        share.setFileId(fileId);
        share.setOwnerId(ownerId);
        share.setSharedWithId(sharedWithId);
        share.setPermission(permission);
        share.setAccessToken(generateAccessToken());
        share.setActive(true);

        return repository.save(share);
    }

    @Transactional
    public void revokeShare(String shareId, String ownerId) {
        Share share = repository.findById(shareId)
                .orElseThrow(() -> new IllegalArgumentException("Share not found"));

        if (!share.getOwnerId().equals(ownerId)) {
            throw new SecurityException("Only the owner can revoke a share");
        }

        share.setActive(false);
        repository.save(share);
    }

    public List<Share> getSharesByFile(String fileId) {
        return repository.findByFileIdAndIsActiveTrue(fileId);
    }

    public List<Share> getSharesByOwner(String ownerId) {
        return repository.findByOwnerIdAndIsActiveTrue(ownerId);
    }

    public List<Share> getSharesByUser(String userId) {
        return repository.findBySharedWithIdAndIsActiveTrue(userId);
    }

    public Optional<Share> validateAccessToken(String accessToken) {
        return repository.findByAccessTokenAndIsActiveTrue(accessToken);
    }

    private String generateAccessToken() {
        return UUID.randomUUID().toString();
    }
}