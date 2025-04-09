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

    @Operation(summary = "Share a file", description = "Share a file with another user")
    @PostMapping
    public ResponseEntity<Share> shareFile(
            @RequestParam String fileId,
            @RequestParam String ownerId,
            @RequestParam String sharedWithId,
            @RequestParam Share.Permission permission) {
        return ResponseEntity.ok(shareService.createShare(fileId, ownerId, sharedWithId, permission));
    }

    @Operation(summary = "Revoke a share", description = "Revoke access to a shared file")
    @DeleteMapping("/{shareId}")
    public ResponseEntity<Void> revokeShare(
            @PathVariable String shareId,
            @RequestParam String ownerId) {
        shareService.revokeShare(shareId, ownerId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get shares by file", description = "Get all active shares for a file")
    @GetMapping("/file/{fileId}")
    public ResponseEntity<List<Share>> getSharesByFile(@PathVariable String fileId) {
        return ResponseEntity.ok(shareService.getSharesByFile(fileId));
    }

    @Operation(summary = "Get shares by owner", description = "Get all files shared by a user")
    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<Share>> getSharesByOwner(@PathVariable String ownerId) {
        return ResponseEntity.ok(shareService.getSharesByOwner(ownerId));
    }

    @Operation(summary = "Get shares by user", description = "Get all files shared with a user")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Share>> getSharesByUser(@PathVariable String userId) {
        return ResponseEntity.ok(shareService.getSharesByUser(userId));
    }

    @Operation(summary = "Validate access token", description = "Validate an access token for a shared file")
    @GetMapping("/validate/{accessToken}")
    public ResponseEntity<Share> validateAccessToken(@PathVariable String accessToken) {
        return shareService.validateAccessToken(accessToken)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
} 