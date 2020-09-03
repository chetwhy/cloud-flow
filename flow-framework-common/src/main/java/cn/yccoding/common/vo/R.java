package cn.yccoding.common.vo;

import cn.yccoding.common.contant.ResultCodeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author YC
 * @create 2020/3/3 统一返回结果类
 */
@Accessors(chain = true)
@Data
public class R {
    // 响应是否成功
    private Boolean success;

    // 响应码
    private Integer code;

    // 响应消息
    private String message;

    // 响应数据
    private Map<String, Object> data = new HashMap<>();

    // 私有构造器
    private R() {

    }

    // 通用成功
    public static R ok() {
        return new R()
                .setSuccess(ResultCodeEnum.SUCCESS.getSuccess())
                .setCode(ResultCodeEnum.SUCCESS.getCode())
                .setMessage(ResultCodeEnum.SUCCESS.getMessage());
    }

    // 通用失败
    public static R error() {
        return new R()
                .setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess())
                .setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode())
                .setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
    }

    // 自定义返回信息
    public static R setResult(ResultCodeEnum result) {
        return new R()
                .setSuccess(result.getSuccess())
                .setCode(result.getCode())
                .setMessage(result.getMessage());
    }

    /** ------------使用链式编程，返回类本身-----------**/
    // 自定义返回数据
    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    // 通用设置data
    public R data(String key,Object value) {
        this.data.put(key, value);
        return this;
    }

    // 自定义状态信息
    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    // 自定义状态码
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    // 自定义返回结果
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }
}
