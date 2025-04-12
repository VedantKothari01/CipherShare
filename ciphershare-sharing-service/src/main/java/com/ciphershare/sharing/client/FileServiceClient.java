package com.ciphershare.sharing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "file-service")
public interface FileServiceClient {
    @GetMapping("/api/files/url/{fileId}")
    String getFileUrl(@PathVariable String fileId);
}
