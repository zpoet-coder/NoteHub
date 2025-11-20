package cn.zpoet.notehub.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 分页数据包装类
 *
 * 封装分页查询结果，包含总记录数和数据列表。
 *
 * @param <T> 数据项的类型
 * @author zpoet
 * @since 2025/11/19
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagination<T> {

    /**
     * 总共有多少条
     */
    private Long total;

    /**
     * 数据列表
     */
    private List<T> list;

}
