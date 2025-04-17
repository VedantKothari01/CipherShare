package com.ciphershare.sharing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "file-service", url = "http://localhost:8081")
public interface FileServiceClient {
    @GetMapping("/api/files/url/{fileID}")
    String getFileUrl(@PathVariable("fileID") String fileId);
}