package com.ciphershare.file.service;

import com.ciphershare.file.entity.File;
import com.ciphershare.file.repository.FileRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Optional;

@Service
public class FileService {

    private final WebClient webClient;
    private final FileRepository fileRepository;

    @Value("${pinata.apiKey}")
    private String pinataApiKey;

    @Value("${pinata.apiSecret}")
    private String pinataApiSecret;

    private static final String PINATA_GATEWAY_URL = "https://gateway.pinata.cloud/ipfs/";

    @Autowired
    public FileService(WebClient.Builder webClientBuilder, FileRepository fileRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.pinata.cloud").build();
        this.fileRepository = fileRepository;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        String response = webClient.post()
                .uri("/pinning/pinFileToIPFS")
                .header("pinata_api_key", pinataApiKey)
                .header("pinata_secret_api_key", pinataApiSecret)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JSONObject jsonResponse = new JSONObject(response);
        String cid = jsonResponse.getString("IpfsHash");

        File newFile = new File();
        newFile.setFileName(file.getOriginalFilename());
        newFile.setFileType(file.getContentType());
        newFile.setFileSize(file.getSize());
        newFile.setIpfsCid(cid);
        fileRepository.save(newFile);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", "File uploaded successfully!");
        responseJson.put("fileId", newFile.getFileID());
        responseJson.put("cid", cid);
        responseJson.put("fileUrl", PINATA_GATEWAY_URL + cid);

        return responseJson.toString();
    }

    public String getFileUrl(String fileId) {
        Optional<File> fileOptional = fileRepository.findById(fileId);
        if (fileOptional.isEmpty()) {
            throw new RuntimeException("File not found for ID: " + fileId);
        }
        File file = fileOptional.get();
        return PINATA_GATEWAY_URL + file.getIpfsCid();
    }

    @Transactional
    public void deleteFile(String fileId) {
        File file = fileRepository.findById(fileId).orElseThrow(() -> new RuntimeException("File not found"));
        String cid = file.getIpfsCid();

        webClient.delete()
                .uri("/pinning/unpin/" + cid)
                .header("pinata_api_key", pinataApiKey)
                .header("pinata_secret_api_key", pinataApiSecret)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        fileRepository.deleteById(fileId);
    }
}
