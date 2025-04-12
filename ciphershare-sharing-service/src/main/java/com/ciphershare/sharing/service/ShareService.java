package com.ciphershare.sharing.service;

import com.ciphershare.sharing.client.FileServiceClient;
import com.ciphershare.sharing.client.UserServiceClient;
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

    @Autowired
    private FileServiceClient fileServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Transactional
    public Share createShare(String fileId, String ownerId, String sharedWithId, Share.Permission permission) {
        // Verify file exists
        fileServiceClient.getFileUrl(fileId); // will throw if not found

        // Verify user exists
        if (!userServiceClient.checkUserExists(sharedWithId)) {
            throw new IllegalArgumentException("Target user does not exist");
        }

        if (repository.existsByFileIdAndSharedWithIdAndIsActiveTrue(fileId, sharedWithId)) {
            throw new IllegalArgumentException("File already shared with this user");
        }

        Share share = new Share();
        share.setFileId(fileId);
        share.setOwnerId(ownerId);
        share.setSharedWithId(sharedWithId);
        share.setPermission(permission);
        share.setAccessToken(UUID.randomUUID().toString());
        share.setActive(true);

        return repository.save(share);
    }

    @Transactional
    public void revokeShare(String shareId, String ownerId) {
        Share share = repository.findById(shareId)
                .orElseThrow(() -> new IllegalArgumentException("Share not found"));

        if (!share.getOwnerId().equals(ownerId)) {
            throw new SecurityException("Only the owner can revoke");
        }

        share.setActive(false);
        repository.save(share);
    }

    public List<Share> getSharesByUser(String userId) {
        return repository.findBySharedWithIdAndIsActiveTrue(userId);
    }

    public Optional<Share> validateAccessToken(String accessToken) {
        return repository.findByAccessTokenAndIsActiveTrue(accessToken);
    }
}
