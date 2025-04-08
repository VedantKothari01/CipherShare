package com.ciphershare.file.service;

import com.ciphershare.file.entity.File;
import com.ciphershare.file.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Value("${pinata.apiKey}")
    private String pinataApiKey;

    @Value("${pinata.apiSecret}")
    private String pinataApiSecret;

    private static final String PINATA_UPLOAD_URL = "https://api.pinata.cloud/pinning/pinFileToIPFS";
    private static final String PINATA_DELETE_URL = "https://api.pinata.cloud/pinning/unpin/";

    private final RestTemplate restTemplate = new RestTemplate();

    public File uploadFile(MultipartFile file) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("pinata_api_key", pinataApiKey);
        headers.set("pinata_secret_api_key", pinataApiSecret);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultipartFile> requestEntity = new HttpEntity<>(file, headers);
        ResponseEntity<String> response = restTemplate.exchange(PINATA_UPLOAD_URL, HttpMethod.POST, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            String cid = jsonResponse.getString("IpfsHash");

            // Save file metadata in database
            File savedFile = new File();
            savedFile.setFileName(file.getOriginalFilename());
            savedFile.setFileSize(file.getSize());
            savedFile.setFileType(file.getContentType());
            savedFile.setEncryptedPath("https://gateway.pinata.cloud/ipfs/" + cid); // IPFS Gateway URL

            return fileRepository.save(savedFile);
        } else {
            throw new RuntimeException("Failed to upload file to Pinata: " + response.getBody());
        }
    }

    public String getFileUrl(String fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
        return file.getEncryptedPath();
    }

    public void deleteFile(String fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
        String cid = file.getEncryptedPath().replace("https://gateway.pinata.cloud/ipfs/", "");

        HttpHeaders headers = new HttpHeaders();
        headers.set("pinata_api_key", pinataApiKey);
        headers.set("pinata_secret_api_key", pinataApiSecret);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        restTemplate.exchange(PINATA_DELETE_URL + cid, HttpMethod.DELETE, requestEntity, String.class);

        fileRepository.deleteById(fileId);
    }
}
