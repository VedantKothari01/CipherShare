package com.ciphershare.file.controller;

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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String response = fileService.uploadFile(file);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/url/{fileId}")
    public ResponseEntity<String> getFileUrl(@PathVariable String fileId) {
        String fileUrl = fileService.getFileUrl(fileId);
        return ResponseEntity.ok(fileUrl);
    }

    @DeleteMapping("/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileId) {
        try {
            fileService.deleteFile(fileId);
            return ResponseEntity.ok("File deleted successfully!");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error deleting file: " + e.getMessage());
        }
    }
}
