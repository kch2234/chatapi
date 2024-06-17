package com.react.chat.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jsonwebtoken.*;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

// JWT 관련 처리해줄 클래스
@Slf4j
@Component
public class JWTUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    private static String key = "SGVsbG9Kc29uV2ViVG9rZW5BdXRoZW50aWNhdGlvbldpdGhTcHJpbmdCb290UHJvamVjdFNlY3JldEtleQ";

    public static String generateToken(Map<String, Object> valueMap, int min) {
        SecretKey secretKey = null;
        try {
            secretKey = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
            log.info("secretKey : {}", secretKey);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e.getMessage());
        }
        String jwtStr = Jwts.builder()
                .setHeader(Map.of("typ", "JWT"))
                .setClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .serializeToJsonWith(new JacksonSerializer<>(objectMapper))
                .signWith(secretKey)
                .compact();
        log.info("jwtStr : {}", jwtStr);

        return jwtStr;
    }

    // 토큰 유효성 검증 메서드 : Claim 리턴
    public static Map<String, Object> validateToken(String token) {
        Map<String, Object> claim = null;
        SecretKey secretKey = null;

        try {
            secretKey = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
            claim = Jwts.parserBuilder()
                    .setSigningKey(secretKey) // 비밀키 세팅
                    .build()
                    .parseClaimsJws(token) // 파싱 및 검증 -> 실패하면 에러 발생 -> catch 로 잡기
                    .getBody();// claim 리턴
        } catch (MalformedJwtException e) { // 잘못된 형식의 토큰 예외
            throw new CustomJWTException("Malformed");
        } catch (ExpiredJwtException e) { // 만료된 토큰 예외
            throw new CustomJWTException("Expired");
        } catch (InvalidClaimException e) { // 유효하지 않는 Claim시 예외
            throw new CustomJWTException("Invalid");
        } catch (JwtException e) { // 그 외 Jwt 관련 예외
            log.info("JwtException : {}", e.getMessage());
            throw new CustomJWTException("JWTError");
        } catch (Exception e) { // 그 외 나머지 예외
            throw new CustomJWTException("Error");
        }
        return claim;
    }

    // 토큰에서 사용자 정보 추출 메서드
    public static String getUsernameFromToken(String token) {
        return (String) validateToken(token).get("username");
    }

    // 토큰에서 사용자 ID 추출 메서드
    public static Long getUserIdFromToken(String token) {
        return Long.parseLong(validateToken(token).get("userId").toString());
    }

    // 토큰 유효성 확인 메서드
    public static boolean isValidToken(String token) {
        try {
            validateToken(token);
            return true;
        } catch (CustomJWTException e) {
            log.info("Token validation error: {}", e.getMessage());
            return false;
        }
    }
}
