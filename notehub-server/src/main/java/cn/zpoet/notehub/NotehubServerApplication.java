package cn.zpoet.notehub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * NoteHub 应用启动类
 *
 * Spring Boot 应用主入口，负责启动整个应用程序。
 *
 * @author zpoet
 * @since 2025/11/19
 */
@SpringBootApplication
public class NotehubServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotehubServerApplication.class, args);
    }

}
