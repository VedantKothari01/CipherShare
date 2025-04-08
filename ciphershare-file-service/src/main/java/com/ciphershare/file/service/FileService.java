package com.ciphershare.file.service;

import com.ciphershare.file.entity.File;
import com.ciphershare.file.repository.FileRepository;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.util.UUID;


@Service
public class FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
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
        String pinataFileId = jsonResponse.getString("id");  // Pinata's File ID

        File newFile = new File();
        newFile.setFileID(pinataFileId);  // Store Pinata's File ID as fileID
        newFile.setFileName(file.getOriginalFilename());
        newFile.setFileType(file.getContentType());
        newFile.setFileSize(file.getSize());
        newFile.setIpfsCid(cid);
        fileRepository.save(newFile);

        JSONObject responseJson = new JSONObject();
        responseJson.put("message", "File uploaded successfully!");
        responseJson.put("cid", cid);
        responseJson.put("fileId", pinataFileId);
        responseJson.put("fileUrl", PINATA_GATEWAY_URL + cid);

        return responseJson.toString();
    }

    public String getFileUrl(String fileId) {
        Optional<File> fileEntity = fileRepository.findByFileID(fileId);

        if (fileEntity.isEmpty()) {
            throw new RuntimeException("File not found");
        }
        return PINATA_GATEWAY_URL + fileEntity.get().getIpfsCid();
    }

    @Transactional
    public void deleteFile(String fileId) {
        Optional<File> fileEntity = fileRepository.findByFileID(fileId);

        if (fileEntity.isEmpty()) {
            throw new RuntimeException("File not found, deletion failed");
        }

        // Remove from Pinata using File ID
        webClient.delete()
                .uri("/pinning/unpin/" + fileId)
                .header("pinata_api_key", pinataApiKey)
                .header("pinata_secret_api_key", pinataApiSecret)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        fileRepository.delete(fileEntity.get());
    }
}
