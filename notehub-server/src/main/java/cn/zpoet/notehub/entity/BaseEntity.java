package cn.zpoet.notehub.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 实体基类，提供统一的审计字段
 *
 * 包含创建时间、更新时间和逻辑删除标记，所有实体类继承此基类。
 * 审计字段由 MyBatis-Plus 自动填充，无需手动设置。
 *
 * @author zpoet
 * @since 2025/11/19
 */
@Data
public class BaseEntity {

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic(value = "false", delval = "true")
    @TableField(fill = FieldFill.INSERT)
    private Boolean deleted;
}
