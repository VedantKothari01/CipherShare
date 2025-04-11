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
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);
    private static final int MAX_RETRIES = 3;
    private static final int CHUNK_SIZE = 5 * 1024 * 1024; // 5MB chunks

    private final WebClient webClient;
    private final FileRepository fileRepository;
    private final ConcurrentHashMap<String, SecretKey> fileKeys = new ConcurrentHashMap<>();

    @Value("${pinata.api.key}")
    private String pinataApiKey;

    @Value("${pinata.api.secret}")
    private String pinataApiSecret;

    private static final String PINATA_GATEWAY_URL = "https://gateway.pinata.cloud/ipfs/";

    @Autowired
    public FileService(WebClient.Builder webClientBuilder, FileRepository fileRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api.pinata.cloud").build();
        this.fileRepository = fileRepository;
    }

    @Retryable(
        value = {WebClientResponseException.class, IOException.class},
        maxAttempts = MAX_RETRIES,
        backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public String uploadFile(MultipartFile file) throws IOException {
        try {
            // Generate encryption key
            SecretKey key = generateKey();
            String fileId = UUID.randomUUID().toString();
            fileKeys.put(fileId, key);

            // Encrypt file
            byte[] encryptedData = encryptFile(file.getBytes(), key);

            // Split into chunks if needed
            byte[][] chunks = splitIntoChunks(encryptedData);

            // Upload each chunk to IPFS
            String[] chunkCids = new String[chunks.length];
            for (int i = 0; i < chunks.length; i++) {
                chunkCids[i] = uploadChunkToIPFS(chunks[i], file.getOriginalFilename() + "_chunk_" + i);
            }

            // Store file metadata
            File newFile = new File();
            newFile.setFileID(fileId);
            newFile.setFileName(file.getOriginalFilename());
            newFile.setFileType(file.getContentType());
            newFile.setFileSize(file.getSize());
            newFile.setIpfsCid(String.join(",", chunkCids));
            newFile.setPinataFileId(String.join(",", chunkCids));
            newFile.setEncrypted(true);

            fileRepository.save(newFile);

            JSONObject responseJson = new JSONObject();
            responseJson.put("message", "File uploaded and encrypted successfully!");
            responseJson.put("fileId", fileId);
            responseJson.put("chunks", chunkCids.length);
            responseJson.put("fileUrl", PINATA_GATEWAY_URL + chunkCids[0]);

            return responseJson.toString();
        } catch (Exception e) {
            logger.error("Error uploading file: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }

    private String uploadChunkToIPFS(byte[] chunk, String filename) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(chunk) {
            @Override
            public String getFilename() {
                return filename;
            }
        });

        try {
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
            return jsonResponse.getString("IpfsHash");
        } catch (WebClientResponseException e) {
            logger.error("Pinata API error: {}", e.getResponseBodyAsString());
            throw new RuntimeException("Failed to upload chunk to IPFS: " + e.getMessage());
        }
    }

    private byte[][] splitIntoChunks(byte[] data) {
        int numChunks = (int) Math.ceil((double) data.length / CHUNK_SIZE);
        byte[][] chunks = new byte[numChunks][];
        
        for (int i = 0; i < numChunks; i++) {
            int start = i * CHUNK_SIZE;
            int length = Math.min(CHUNK_SIZE, data.length - start);
            chunks[i] = new byte[length];
            System.arraycopy(data, start, chunks[i], 0, length);
        }
        
        return chunks;
    }

    private SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(256);
        return keyGen.generateKey();
    }

    private byte[] encryptFile(byte[] data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        
        byte[] encrypted = cipher.doFinal(data);
        byte[] result = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);
        
        return result;
    }

    private byte[] decryptFile(byte[] encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[16];
        System.arraycopy(encryptedData, 0, iv, 0, iv.length);
        
        byte[] encrypted = new byte[encryptedData.length - iv.length];
        System.arraycopy(encryptedData, iv.length, encrypted, 0, encrypted.length);
        
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
        return cipher.doFinal(encrypted);
    }

    @Retryable(
        value = {WebClientResponseException.class},
        maxAttempts = MAX_RETRIES,
        backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public String getFileUrl(String fileId) {
        logger.info("Fetching file with ID: {}", fileId);

        Optional<File> fileEntity = fileRepository.findById(fileId);

        if (fileEntity.isEmpty()) {
            logger.error("File not found in database for ID: {}", fileId);
            throw new RuntimeException("File not found");
        }

        String fileUrl = PINATA_GATEWAY_URL + fileEntity.get().getIpfsCid().split(",")[0];
        logger.info("File found: {}", fileUrl);
        return fileUrl;
    }

    @Transactional
    @Retryable(
        value = {WebClientResponseException.class},
        maxAttempts = MAX_RETRIES,
        backoff = @Backoff(delay = 1000, multiplier = 2)
    )
    public void deleteFile(String fileId) {
        logger.info("Attempting to delete file with ID: {}", fileId);

        Optional<File> fileEntity = fileRepository.findById(fileId);

        if (fileEntity.isEmpty()) {
            logger.error("File not found in database, cannot delete: {}", fileId);
            throw new RuntimeException("File not found, deletion failed");
        }

        String[] cids = fileEntity.get().getIpfsCid().split(",");
        
        for (String cid : cids) {
            try {
                webClient.delete()
                        .uri("/pinning/unpin/" + cid)
                        .header("pinata_api_key", pinataApiKey)
                        .header("pinata_secret_api_key", pinataApiSecret)
                        .retrieve()
                        .bodyToMono(String.class)
                        .block();
            } catch (WebClientResponseException e) {
                logger.error("Failed to unpin chunk {}: {}", cid, e.getMessage());
                // Continue with other chunks even if one fails
            }
        }

        fileRepository.delete(fileEntity.get());
        fileKeys.remove(fileId);
        logger.info("File deleted successfully from database and Pinata");
    }
}
