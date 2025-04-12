package com.ciphershare.sharing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service")
public interface UserServiceClient {
    @GetMapping("/api/users/{userId}/exists")
    boolean checkUserExists(@PathVariable("userId") String userId); // Corrected
}