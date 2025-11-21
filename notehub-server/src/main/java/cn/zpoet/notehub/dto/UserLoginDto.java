package cn.zpoet.notehub.dto;

import lombok.Data;

/**
 * 用户登录请求 DTO
 *
 * 封装用户登录时提交的用户名和密码信息。
 *
 * @author zpoet
 * @since 2025/11/19
 */
@Data
public class UserLoginDto {

    private String userName;

    private String passWord;
}
