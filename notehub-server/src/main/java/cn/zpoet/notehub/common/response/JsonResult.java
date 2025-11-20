package cn.zpoet.notehub.common.response;

import cn.zpoet.notehub.common.model.Pagination;
import cn.zpoet.notehub.constant.enums.OAuth2Error;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 统一 JSON 响应结果类
 *
 * 封装 API 接口的统一返回格式，包含状态码、消息和数据。
 * 提供成功、失败和错误的快捷工厂方法。
 *
 * @param <T> 返回数据的类型
 * @author zpoet
 * @since 2025/11/19
 */
@Getter
@Setter
public class JsonResult<T> implements Serializable {

    private static final int SUCCESS = 200;

    private static final int FAIL = 100;

    /**
     * 返回状态码
     */
    private Integer status;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回内容
     */
    private T data;

    private JsonResult() {
    }

    /**
     * 自定义返回状态码
     */
    private static <T> JsonResult<T> result(int status, String message, T data) {
        var result = new JsonResult<T>();
        result.status = status;
        result.message = message;
        result.data = data;
        return result;
    }

    /**
     * 自定义返回状态码
     */
    private static <T> JsonResult<T> result(int status, T data) {
        return result(status, "ok", data);
    }

    /**
     * 自定义返回状态码 [不建议使用，建议扩展方法]
     */
    public static <T> JsonResult<T> result(int status, String message) {
        return result(status, message, null);
    }

    /**
     * 操作成功
     */
    public static <T> JsonResult<T> success() {
        return result(SUCCESS, "ok");
    }

    /**
     * 操作成功（分页数据）
     */
    public static <T> JsonResult<Pagination<T>> success(long total, List<T> list) {
        return success(new Pagination<T>(total, list));
    }

    /**
     * 操作成功（返回数据）
     */
    public static <T> JsonResult<T> success(T data) {
        return result(SUCCESS, "ok", data);
    }

    /**
     * 操作成功（返回消息和数据）
     */
    public static <T> JsonResult<T> success(String message, T data) {
        return result(SUCCESS, message, data);
    }

    /**
     * 操作失败
     */
    public static <T> JsonResult<T> fail(String message) {
        return result(FAIL, message, null);
    }

    /**
     * 操作失败
     */
    public static <T> JsonResult<T> fail(String message, Class<T> data) {
        return result(FAIL, message, null);
    }

    /**
     * 操作失败
     */
    public static <T> JsonResult<T> fail(String message, T data) {
        return result(FAIL, message, null);
    }

    /**
     * 异常错误
     */
    public static <T> JsonResult<T> error(int status, String message) {
        return result(status, message, null);
    }

    /**
     * 异常错误
     */
    public static <T> JsonResult<T> error(OAuth2Error error) {
        return result(error.getStatus(), error.getError(), null);
    }

    /**
     * 异常错误
     */
    public static <T> JsonResult<T> error(int status, String message, Class<T> data) {
        return result(status, message, null);
    }

    /**
     * 获取 成功的常量
     */
    public static int getSuccess() {
        return SUCCESS;
    }

    /**
     * 获取 失败的常量
     */
    public static int getFail() {
        return FAIL;
    }
}
