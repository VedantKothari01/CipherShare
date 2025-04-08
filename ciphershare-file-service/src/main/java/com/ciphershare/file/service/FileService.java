package com.ciphershare.file.service;

import com.ciphershare.file.entity.File;
import com.ciphershare.file.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import org.json.JSONObject;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {

    private final WebClient webClient;
    private final RestTemplate restTemplate;


    @Autowired
    private FileRepository fileRepository;

    @Value("${pinata.apiKey}")
    private String pinataApiKey;

    @Value("${pinata.apiSecret}")
    private String pinataApiSecret;

    private static final String PINATA_UPLOAD_URL = "https://api.pinata.cloud/pinning/pinFileToIPFS";
    private static final String PINATA_DELETE_URL = "https://api.pinata.cloud/pinning/unpin/";
    private static final String PINATA_GATEWAY_URL = "https://gateway.pinata.cloud/ipfs/";

    @Autowired
    public FileService(WebClient.Builder webClientBuilder, FileRepository fileRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.pinata.cloud").build();
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
    public String uploadFile(MultipartFile file) throws IOException {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        }).contentType(MediaType.MULTIPART_FORM_DATA);

        return webClient.post()
                .uri("/pinning/pinFileToIPFS")
                .header("pinata_api_key", pinataApiKey)
                .header("pinata_secret_api_key", pinataApiSecret)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(builder.build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
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