package com.example.springJWT.global.util;

import com.example.springJWT.config.JwtConfig;
import com.example.springJWT.global.exeption.CustomException;
import com.example.springJWT.global.exeption.ErrorCode;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil {
    private final JwtConfig jwtConfig;

    public JwtUtil(final JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtConfig.SECRET_KEY).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.get("userId").toString());
    }

    public Boolean validateToken(String token) {
        try {
            log.info("SECRET KEY :"+ jwtConfig.SECRET_KEY);

            Jwts.parser().setSigningKey(jwtConfig.SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.info("Sign 오류");
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("잘못된 토큰");
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        } catch (MalformedJwtException e) {
            log.info("토큰 형식 오류");
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("토큰 유효기간 만료");
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        } catch (NullPointerException e) {
            log.info("토큰 없음");
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 토큰");
            throw new CustomException(ErrorCode.INVALID_AUTH_TOKEN);
        }
    }
}
