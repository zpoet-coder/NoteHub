package cn.zpoet.notehub.security;

import cn.zpoet.notehub.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 工具类
 * <p>
 * 功能：
 * - 生成 Access Token 和 Refresh Token
 * - 解析和验证 Token
 * - 提取用户信息
 * <p>
 * 安全特性：
 * - 使用 HS512 签名算法
 * - 密钥从配置文件读取（支持环境变量）
 * - Token 有效期管理
 * - 完整的异常处理
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    /**
     * 生成签名密钥
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 生成 Access Token
     *
     * @param userId 用户 ID
     * @param email  用户邮箱
     * @return JWT Token
     */
    public String generateAccessToken(String userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("type", "access");

        return generateToken(userId, claims, jwtProperties.getAccessTokenExpiration());
    }

    /**
     * 生成 Refresh Token
     *
     * @param userId 用户 ID
     * @return JWT Refresh Token
     */
    public String generateRefreshToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");

        return generateToken(userId, claims, jwtProperties.getRefreshTokenExpiration());
    }

    /**
     * 生成 Token（通用方法）
     *
     * @param subject    主题（通常是用户 ID）
     * @param claims     自定义声明
     * @param expiration 过期时间（毫秒）
     * @return JWT Token
     */
    private String generateToken(String subject, Map<String, Object> claims, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(), Jwts.SIG.HS512)
                .compact();
    }

    /**
     * 从 Token 中提取用户 ID
     *
     * @param token JWT Token
     * @return 用户 ID
     */
    public String getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.getSubject() : null;
    }

    /**
     * 从 Token 中提取邮箱
     *
     * @param token JWT Token
     * @return 用户邮箱
     */
    public String getEmailFromToken(String token) {
        Claims claims = parseToken(token);
        return claims != null ? claims.get("email", String.class) : null;
    }

    /**
     * 验证 Token 是否有效
     *
     * @param token JWT Token
     * @return true: 有效, false: 无效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            log.error("Token 验证失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 检查 Token 是否过期
     *
     * @param token JWT Token
     * @return true: 已过期, false: 未过期
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            if (claims == null) {
                return true;
            }
            return claims.getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * 解析 Token
     *
     * @param token JWT Token
     * @return Claims 对象
     */
    private Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("Token 已过期: {}", e.getMessage());
            throw new RuntimeException("Token 已过期", e);
        } catch (MalformedJwtException e) {
            log.error("Token 格式错误: {}", e.getMessage());
            throw new RuntimeException("Token 格式错误", e);
        } catch (SignatureException e) {
            log.error("Token 签名验证失败: {}", e.getMessage());
            throw new RuntimeException("Token 签名无效", e);
        } catch (Exception e) {
            log.error("Token 解析失败: {}", e.getMessage());
            throw new RuntimeException("Token 无效", e);
        }
    }

    /**
     * 刷新 Token（使用 Refresh Token 生成新的 Access Token）
     *
     * @param refreshToken Refresh Token
     * @param email        用户邮箱
     * @return 新的 Access Token
     */
    public String refreshAccessToken(String refreshToken, String email) {
        if (!validateToken(refreshToken)) {
            throw new RuntimeException("Refresh Token 无效");
        }

        String userId = getUserIdFromToken(refreshToken);
        return generateAccessToken(userId, email);
    }
}
