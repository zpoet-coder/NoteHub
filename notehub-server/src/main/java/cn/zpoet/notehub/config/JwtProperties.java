package cn.zpoet.notehub.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置属性类
 *
 * 从 application.yml 中读取 JWT 相关配置，包括签名密钥和 Token 有效期。
 * 生产环境建议通过环境变量注入密钥。
 *
 * @author zpoet
 * @since 2025/11/19
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * JWT 签名密钥（Base64 编码）
     * 生产环境应通过环境变量 JWT_SECRET 注入
     */
    private String secret;

    /**
     * Access Token 有效期（毫秒）
     * 默认 2 小时
     */
    private Long accessTokenExpiration = 7200000L;

    /**
     * Refresh Token 有效期（毫秒）
     * 默认 7 天
     */
    private Long refreshTokenExpiration = 604800000L;
}
