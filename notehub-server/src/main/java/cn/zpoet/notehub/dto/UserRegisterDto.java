package cn.zpoet.notehub.dto;

import lombok.Data;

/**
 * 用户注册请求 DTO
 *
 * 封装用户注册时提交的用户名、密码和邮箱信息。
 *
 * @author zpoet
 * @since 2025/11/19
 */
@Data
public class UserRegisterDto {

    private String userName;

    private String passWord;

    private String email;
}
