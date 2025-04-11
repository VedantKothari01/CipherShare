package com.ciphershare.sharing.repository;

import com.ciphershare.sharing.entity.Share;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShareRepository extends JpaRepository<Share, String> {
    List<Share> findByFileIdAndIsActiveTrue(String fileId);
    List<Share> findByOwnerIdAndIsActiveTrue(String ownerId);
    List<Share> findBySharedWithIdAndIsActiveTrue(String sharedWithId);
    Optional<Share> findByAccessTokenAndIsActiveTrue(String accessToken);
    boolean existsByFileIdAndSharedWithIdAndIsActiveTrue(String fileId, String sharedWithId);
}