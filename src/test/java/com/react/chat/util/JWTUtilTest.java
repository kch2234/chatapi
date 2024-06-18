package com.react.chat.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JWTUtilTest {

    @Test
    public void testGenerateAndValidateToken() {
        // given
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", 1L);
        claims.put("email", "test@test.com");
        claims.put("nickname", "testUser");
        claims.put("birth", LocalDateTime.of(2024, 5, 31, 17, 23));
        claims.put("nationality", "한국");
        claims.put("gender", "MALE");
        claims.put("role", "USER");

        // when
        String token = JWTUtil.generateToken(claims, 10);
        Map<String, Object> validatedClaims = JWTUtil.validateToken(token);

        // then
        assertNotNull(token);
        assertNotNull(validatedClaims);
        assertEquals(claims.get("id"), validatedClaims.get("id"));
        assertEquals(claims.get("email"), validatedClaims.get("email"));
        assertEquals(claims.get("nickname"), validatedClaims.get("nickname"));
        assertEquals(claims.get("nationality"), validatedClaims.get("nationality"));
        assertEquals(claims.get("gender"), validatedClaims.get("gender"));
        assertEquals(claims.get("role"), validatedClaims.get("role"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        assertEquals(((LocalDateTime) claims.get("birth")).format(formatter), validatedClaims.get("birth"));
    }
}
