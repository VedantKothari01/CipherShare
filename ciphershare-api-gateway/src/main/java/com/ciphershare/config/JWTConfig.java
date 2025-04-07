package com.ciphershare.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
public class JWTConfig {

    @Bean
    public SecretKey jwtSecretKey() {
        // Generate a 256-bit (32-byte) key for HS256
        String base64Key = Base64.getEncoder().encodeToString(
                Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256).getEncoded()
        );

        System.out.println("Generated JWT Secret Key: " + base64Key);
        return Keys.hmacShaKeyFor(base64Key.getBytes());
    }
}