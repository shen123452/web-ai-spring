package com.hhh.utils;

import com.hhh.config.JwtProperties;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JwtUtilsTest {

    @Test
    void shouldGenerateAndParseToken() {
        JwtProperties jwtProperties = new JwtProperties();
        jwtProperties.setSecretKey("tlias-web-demo-secret-key-please-change-this");
        jwtProperties.setTtl(86_400_000L);

        JwtUtils jwtUtils = new JwtUtils(jwtProperties);
        String token = jwtUtils.generateToken(Map.of("id", 1, "username", "admin"));

        Claims claims = jwtUtils.parseToken(token);
        assertThat(((Number) claims.get("id")).intValue()).isEqualTo(1);
        assertThat(claims.get("username", String.class)).isEqualTo("admin");
    }
}
