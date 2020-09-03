package cn.yccoding.common.contant;

import lombok.Getter;

/**
 * @Author YC
 * @create 2020/3/3
 * 结果信息枚举
 */
@Getter
public enum  ResultCodeEnum {
    SUCCESS(true,20000,"成功"),
    UNKNOWN_ERROR(false,20001,"未知错误"),
    PARAM_ERROR(false,20002,"参数错误"),
    NULL_POINTER(false,20003,"空指针异常")
    ;


    /**
     * 响应是否成功
     */
    private Boolean success;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
