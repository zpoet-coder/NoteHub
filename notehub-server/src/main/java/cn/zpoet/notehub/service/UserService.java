package cn.zpoet.notehub.service;

import cn.zpoet.notehub.entity.User;

/**
 * 用户服务接口
 *
 * 提供用户注册、登录等核心业务功能。
 *
 * @author zpoet
 * @since 2025/11/19
 */
public interface UserService {

    User register(User user);

    String login(String userName, String passWord);
}
