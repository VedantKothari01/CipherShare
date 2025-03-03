package com.ciphershare.sharing.controller;

import com.ciphershare.sharing.entity.SharedFile;
import com.ciphershare.sharing.service.SharingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sharing")
public class SharingController {
    @Autowired
    private SharingService sharingService;

    @PostMapping
    public ResponseEntity<SharedFile> shareFile(@RequestBody SharedFile sharedFile) {
        return ResponseEntity.ok(sharingService.shareFile(sharedFile));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SharedFile>> getSharedFiles(@PathVariable String userId) {
        return ResponseEntity.ok(sharingService.getSharedFilesForUser(userId));
    }

    @PutMapping("/{shareID}")
    public ResponseEntity<SharedFile> updateShare(@PathVariable String shareID, @RequestBody SharedFile sharedFile) {
        return ResponseEntity.ok(sharingService.updateShare(shareID, sharedFile));
    }

    @DeleteMapping("/{shareID}")
    public ResponseEntity<Void> revokeShare(@PathVariable String shareID) {
        sharingService.revokeShare(shareID);
        return ResponseEntity.noContent().build();
    }
}
