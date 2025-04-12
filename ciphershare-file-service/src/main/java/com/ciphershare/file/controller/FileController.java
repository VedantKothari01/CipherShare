package com.ciphershare.file.controller;

import com.ciphershare.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Upload a file", description = "Uploads a file to IPFS and stores metadata")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @Parameter(description = "File to upload") @RequestParam("file") MultipartFile file) throws IOException {
        String response = fileService.uploadFile(file);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get file URL", description = "Retrieves the IPFS URL for a file")
    @GetMapping("/url/{fileId}")
    public ResponseEntity<String> getFileUrl(
            @Parameter(description = "ID of the file") @PathVariable("fileId") String fileId)
    {
        String fileUrl = fileService.getFileUrl(fileId);
        return ResponseEntity.ok(fileUrl);
    }

    @Operation(summary = "Delete a file", description = "Deletes a file from IPFS and removes metadata")
    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(
            @Parameter(description = "ID of the file to delete") @PathVariable("fileId") String fileId) {
        try {
            fileService.deleteFile(fileId);
            return ResponseEntity.ok("File deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error deleting file: " + e.getMessage());
        }
    }
}