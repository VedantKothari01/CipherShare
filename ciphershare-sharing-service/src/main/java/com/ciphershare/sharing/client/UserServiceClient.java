package com.ciphershare.sharing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "user-service", url = "http://localhost:8084")
public interface UserServiceClient {
    @GetMapping("/api/users/{user_id}/exists")
    boolean checkUserExists(@PathVariable("user_id") String userId);
}