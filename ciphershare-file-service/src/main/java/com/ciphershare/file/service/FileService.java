package com.ciphershare.file.service;

import com.ciphershare.file.entity.File;
import com.ciphershare.file.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private static final String PINATA_GATEWAY_URL = "https://gateway.pinata.cloud/ipfs/";

    private final RestTemplate restTemplate;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
        this.restTemplate = createRestTemplate();
    }

    private RestTemplate createRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new SourceHttpMessageConverter<>());
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }

    public File uploadFile(MultipartFile file) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.set("pinata_api_key", pinataApiKey);
        headers.set("pinata_secret_api_key", pinataApiSecret);
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultipartFile> requestEntity = new HttpEntity<>(file, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(PINATA_UPLOAD_URL, requestEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            String cid = jsonResponse.getString("IpfsHash");

            File savedFile = new File();
            savedFile.setFileName(file.getOriginalFilename());
            savedFile.setFileSize(file.getSize());
            savedFile.setFileType(file.getContentType());
            savedFile.setEncryptedPath(PINATA_GATEWAY_URL + cid);

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
        String cid = file.getEncryptedPath().replace(PINATA_GATEWAY_URL, "");

        HttpHeaders headers = new HttpHeaders();
        headers.set("pinata_api_key", pinataApiKey);
        headers.set("pinata_secret_api_key", pinataApiSecret);

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        restTemplate.exchange(PINATA_DELETE_URL + cid, HttpMethod.DELETE, requestEntity, String.class);

        fileRepository.deleteById(fileId);
    }
}