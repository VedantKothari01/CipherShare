package com.ciphershare.sharing.controller;

import com.ciphershare.sharing.entity.SharedFile;
import com.ciphershare.sharing.service.SharingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Sharing API", description = "Manages file sharing permissions and access")
@RestController
@RequestMapping("/api/sharing")
public class SharingController {

    @Autowired
    private SharingService sharingService;

    @Operation(summary = "Share File", description = "Creates a share record for a file.")
    @PostMapping
    public ResponseEntity<SharedFile> shareFile(@RequestBody SharedFile sharedFile) {
        return ResponseEntity.ok(sharingService.shareFile(sharedFile));
    }

    @Operation(summary = "Get Shared Files for User", description = "Retrieves all shared files for a given user.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SharedFile>> getSharedFiles(@PathVariable String userId) {
        return ResponseEntity.ok(sharingService.getSharedFilesForUser(userId));
    }

    @Operation(summary = "Update Share Record", description = "Updates sharing details for a record.")
    @PutMapping("/{shareID}")
    public ResponseEntity<SharedFile> updateShare(@PathVariable String shareID, @RequestBody SharedFile sharedFile) {
        return ResponseEntity.ok(sharingService.updateShare(shareID, sharedFile));
    }

    @Operation(summary = "Revoke Share", description = "Revokes access to a shared file.")
    @DeleteMapping("/{shareID}")
    public ResponseEntity<Void> revokeShare(@PathVariable String shareID) {
        sharingService.revokeShare(shareID);
        return ResponseEntity.noContent().build();
    }
}
