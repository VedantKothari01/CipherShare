package com.ciphershare.sharing.controller;

import com.ciphershare.sharing.entity.Share;
import com.ciphershare.sharing.service.ShareService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Sharing API", description = "Manages file sharing between users")
@RestController
@RequestMapping("/api/shares")
public class ShareController {

    @Autowired
    private ShareService shareService;

    @PostMapping
    public ResponseEntity<Share> shareFile(
            @RequestParam String fileId,
            @RequestParam String ownerId,
            @RequestParam String sharedWithId,
            @RequestParam Share.Permission permission) {
        return ResponseEntity.ok(
                shareService.createShare(fileId, ownerId, sharedWithId, permission)
        );
    }

    @DeleteMapping("/{shareId}")
    public ResponseEntity<Void> revokeShare(
            @PathVariable String shareId,
            @RequestParam String ownerId) {
        shareService.revokeShare(shareId, ownerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Share>> getUserShares(@PathVariable String userId) {
        return ResponseEntity.ok(shareService.getSharesByUser(userId));
    }

    @GetMapping("/validate/{accessToken}")
    public ResponseEntity<Share> validateToken(@PathVariable String accessToken) {
        return shareService.validateAccessToken(accessToken)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
