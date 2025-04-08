package com.ciphershare.file.controller;

import com.ciphershare.file.entity.File;
import com.ciphershare.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "File API", description = "Handles file uploads, retrieval, and deletion")
@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "Upload File", description = "Uploads a file to IPFS via Pinata")
    @PostMapping("/upload")
    public ResponseEntity<File> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok(fileService.uploadFile(file));
    }

    @Operation(summary = "Get File URL", description = "Retrieves the URL of a stored file")
    @GetMapping("/{fileId}/url")
    public ResponseEntity<String> getFileUrl(@PathVariable String fileId) {
        return ResponseEntity.ok(fileService.getFileUrl(fileId));
    }

    @Operation(summary = "Delete File", description = "Deletes a file from IPFS via Pinata")
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }
}