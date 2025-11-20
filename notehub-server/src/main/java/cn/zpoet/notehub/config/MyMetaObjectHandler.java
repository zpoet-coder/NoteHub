package cn.zpoet.notehub.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * MyBatis-Plus 自动填充处理器
 *
 * 自动填充实体类的审计字段，包括创建时间、更新时间和逻辑删除标记。
 * 在执行 INSERT 和 UPDATE 操作时自动设置这些字段的值。
 *
 * @author zpoet
 * @since 2025/11/19
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "updatedAt", LocalDateTime.class, now);
        this.strictInsertFill(metaObject, "deleted", Boolean.class, false);  // 默认未删除
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, now);
    }
}
