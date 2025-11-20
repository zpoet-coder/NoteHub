package cn.zpoet.notehub.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis-Plus 配置类
 *
 * 配置 MyBatis-Plus 的分页插件和其他增强功能。
 * 支持 PostgreSQL 数据库的分页查询，单页最大限制 1000 条记录。
 *
 * @author zpoet
 * @since 2025/11/19
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 分页插件，指定数据库类型为 PostgreSQL
        PaginationInnerInterceptor paginationInterceptor = new PaginationInnerInterceptor(DbType.POSTGRE_SQL);
        // 设置单页最大限制数量为 1000
        paginationInterceptor.setMaxLimit(1000L);

        // 添加分页插件
        interceptor.addInnerInterceptor(paginationInterceptor);
        return interceptor;
    }
}
