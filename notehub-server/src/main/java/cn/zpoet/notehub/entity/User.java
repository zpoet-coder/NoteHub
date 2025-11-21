package cn.zpoet.notehub.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户实体类
 *
 * 对应数据库 notehub_user 表，存储用户基本信息和认证凭据。
 * 使用雪花算法生成主键，密码字段在序列化时自动忽略。
 *
 * @author zpoet
 * @since 2025/11/19
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@TableName("notehub_user")
public class User extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String userName;

    private String nickName;

    @JsonIgnore
    private String passWord;

    private String email;
}
