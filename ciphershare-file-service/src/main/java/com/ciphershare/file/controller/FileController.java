package com.ciphershare.file.controller;

import com.ciphershare.file.entity.File;
import com.ciphershare.file.entity.FileVersion;
import com.ciphershare.file.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "File API", description = "Handles file metadata and versioning")
@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "Create File", description = "Creates a new file record.")
    @PostMapping
    public ResponseEntity<File> createFile(@RequestBody File file) {
        return ResponseEntity.ok(fileService.createFile(file));
    }

    @Operation(summary = "Get File by ID", description = "Retrieves file details by file ID.")
    @GetMapping("/{fileId}")
    public ResponseEntity<File> getFile(@PathVariable String fileId) {
        return ResponseEntity.ok(fileService.getFile(fileId));
    }

    @Operation(summary = "Update File", description = "Updates an existing file record.")
    @PutMapping("/{fileId}")
    public ResponseEntity<File> updateFile(@PathVariable String fileId, @RequestBody File file) {
        return ResponseEntity.ok(fileService.updateFile(fileId, file));
    }

    @Operation(summary = "Delete File", description = "Deletes a file record.")
    @DeleteMapping("/{fileId}")
    public ResponseEntity<Void> deleteFile(@PathVariable String fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Add File Version", description = "Adds a new version to an existing file.")
    @PostMapping("/versions")
    public ResponseEntity<FileVersion> addFileVersion(@RequestBody FileVersion version) {
        return ResponseEntity.ok(fileService.addFileVersion(version));
    }

    @Operation(summary = "Get File Versions", description = "Retrieves all versions of a file.")
    @GetMapping("/versions/{fileId}")
    public ResponseEntity<List<FileVersion>> getFileVersions(@PathVariable String fileId) {
        return ResponseEntity.ok(fileService.getFileVersions(fileId));
    }
}
