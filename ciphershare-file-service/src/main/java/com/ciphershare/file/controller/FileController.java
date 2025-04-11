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

@Tag(name = "File API", description = "Manages file uploads and downloads")
@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "Upload File", description = "Uploads a file to IPFS.")
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String userId) throws IOException {
        return ResponseEntity.ok(fileService.uploadFile(file, userId));
    }

    @Operation(summary = "Get File URL", description = "Gets the IPFS URL for a file.")
    @GetMapping("/{fileId}")
    public String getFileUrl(@PathVariable String fileId) {
        return fileService.getFileUrl(fileId);
    }

    @Operation(summary = "Delete File", description = "Deletes a file from IPFS.")
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileId, @RequestParam String userId) {
        fileService.deleteFile(fileId, userId);
        return ResponseEntity.noContent().build();
    }
}
