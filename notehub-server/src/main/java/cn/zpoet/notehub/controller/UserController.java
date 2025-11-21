package cn.zpoet.notehub.controller;

import cn.zpoet.notehub.common.response.JsonResult;
import cn.zpoet.notehub.dto.UserLoginDto;
import cn.zpoet.notehub.dto.UserRegisterDto;
import cn.zpoet.notehub.entity.User;
import cn.zpoet.notehub.mapper.UserMapper;
import cn.zpoet.notehub.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户认证控制器
 * <p>
 * 提供用户注册和登录的 REST API 接口。
 * 所有接口路径前缀为 /api/auth。
 *
 * @author zpoet
 * @since 2025/11/19
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/register")
    public JsonResult<User> register(@RequestBody UserRegisterDto userRegisterDto) {
        // 检查是否存在该邮箱的账号
        User user = userMapper.selectOne(new LambdaQueryWrapper<User>()
                .eq(User::getEmail, userRegisterDto.getEmail())
        );
        if (user != null) {
            throw new RuntimeException("已存在由该邮箱绑定的账号");
        } else {
            user = User.builder()
                    .userName(userRegisterDto.getUserName())
                    .nickName(userRegisterDto.getUserName())  // 默认昵称为用户名
                    .passWord(userRegisterDto.getPassWord())
                    .email(userRegisterDto.getEmail())
                    .build();

            return JsonResult.success(userService.register(user));
        }
    }

    @PostMapping("/login")
    public JsonResult<String> login(@RequestBody UserLoginDto UserLoginDto) {
        return JsonResult.success(userService.login(UserLoginDto.getUserName(), UserLoginDto.getPassWord()));
    }

    @PostMapping("/login1")
    public JsonResult<String> login1(@RequestBody UserLoginDto UserLoginDto) {
        return JsonResult.success(userService.login(UserLoginDto.getUserName(), UserLoginDto.getPassWord()));
    }

}
