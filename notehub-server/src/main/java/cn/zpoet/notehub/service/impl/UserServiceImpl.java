package cn.zpoet.notehub.service.impl;

import cn.zpoet.notehub.entity.User;
import cn.zpoet.notehub.mapper.UserMapper;
import cn.zpoet.notehub.security.JwtUtil;
import cn.zpoet.notehub.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 *
 * 实现用户注册和登录的核心业务逻辑。
 * 注册时使用 BCrypt 加密密码，登录时生成 JWT Token。
 *
 * @author zpoet
 * @since 2025/11/19
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(User user) {
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        userMapper.insert(user);
        return user;
    }

    @Override
    public String login(String userName, String passWord) {
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getUserName, userName)
        );

        // 如果存在用户，验证密码
        if (user != null && passwordEncoder.matches(passWord, user.getPassWord())) {
            // 使用新的 JwtUtil 生成 Access Token
            return jwtUtil.generateAccessToken(user.getId().toString(), user.getEmail());
        } else {
            throw new RuntimeException("用户名或密码错误");
        }
    }
}
