package com.ciphershare.file.service;

import com.ciphershare.file.entity.File;
import com.ciphershare.file.entity.FileVersion;
import com.ciphershare.file.repository.FileRepository;
import com.ciphershare.file.repository.FileVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileVersionRepository fileVersionRepository;

    private final S3Client s3Client;
    private final String bucketName;

    @Autowired
    public FileService(
            @Value("${filebase.endpoint}") String endpoint,
            @Value("${filebase.accessKey}") String accessKey,
            @Value("${filebase.secretKey}") String secretKey,
            @Value("${filebase.bucketName}") String bucketName
    ) {
        this.bucketName = bucketName;

        this.s3Client = S3Client.builder()
                .endpointOverride(URI.create(endpoint))
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .region(software.amazon.awssdk.regions.Region.US_EAST_1)
                .build();
    }

    public File uploadFile(MultipartFile file) throws IOException {
        String fileKey = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileKey)
                        .build(),
                RequestBody.fromBytes(file.getBytes())
        );

        // Save metadata in database
        File savedFile = new File();
        savedFile.setFileName(file.getOriginalFilename());
        savedFile.setFileSize(file.getSize());
        savedFile.setFileType(file.getContentType());
        savedFile.setEncryptedPath("https://s3.filebase.com/" + bucketName + "/" + fileKey); // Filebase URL

        return fileRepository.save(savedFile);
    }

    public String getFileUrl(String fileId) {
        return "https://s3.filebase.com/" + bucketName + "/" + fileId;
    }

    public void deleteFile(String fileId) {
        s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(fileId).build());
        fileRepository.deleteById(fileId);
    }

    public List<FileVersion> getFileVersions(String fileId) {
        return fileVersionRepository.findByFileID(fileId);
    }
}
