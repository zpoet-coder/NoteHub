package cn.zpoet.notehub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 安全配置类
 *
 * 配置应用的安全策略，包括 CSRF 禁用、无状态 Session 管理和 BCrypt 密码编码器。
 * 使用 JWT 进行身份认证，不依赖传统的 Session 机制。
 *
 * @author zpoet
 * @since 2025/11/19
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用 CSRF（使用 JWT 时不需要）
                .csrf(AbstractHttpConfigurer::disable)
                // 配置 session 管理为无状态（JWT 方式）
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 配置授权规则
                .authorizeHttpRequests(auth -> auth
                        // 允许所有请求（后续可根据需要细化权限控制）
                        .anyRequest().permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
