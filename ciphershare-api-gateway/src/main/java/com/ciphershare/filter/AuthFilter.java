package com.ciphershare.filter;

import com.ciphershare.config.JWTConfig;
import io.jsonwebtoken.*;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;

@Component
public class AuthFilter implements GlobalFilter, Ordered {

    private final SecretKey secretKey;

    public AuthFilter(JWTConfig jwtConfig) {
        this.secretKey = jwtConfig.jwtSecretKey();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().toString();

        // Skip auth for public endpoints
        if (path.startsWith("/api/public") || path.equals("/fallback/user")) {
            return chain.filter(exchange);
        }

        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            Jws<Claims> claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token.substring(7));

            // Add user info to headers
            exchange.getRequest().mutate()
                    .header("X-User-Email", claims.getPayload().get("email", String.class))
                    .header("X-User-Roles", claims.getPayload().get("roles", String.class))
                    .build();

        } catch (JwtException e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}