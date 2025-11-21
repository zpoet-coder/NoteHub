package cn.zpoet.notehub.mapper;

import cn.zpoet.notehub.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据访问接口
 *
 * 继承 MyBatis-Plus 的 BaseMapper，提供基础 CRUD 操作。
 *
 * @author zpoet
 * @since 2025/11/19
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
