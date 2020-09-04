package cn.yccoding.common.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author YC
 * @create 2020/3/3
 * 结果信息枚举
 */
@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    SUCCESS(true, 20000, "成功"),

    UNKNOWN_ERROR(false, 30001, "未知错误"),

    PARAM_ERROR(false, 30002, "参数错误"),

    NULL_POINTER(false, 30003, "空指针异常"),

    /**
     * member module
     */
    USER_EXISTED(false, 41000, "用户已存在"), OPTCODE_EFFECTIVE(false, 410001, "验证码未过期");


    /**
     * 响应是否成功
     */
    private final boolean success;
    /**
     * 响应状态码
     */
    private final int code;
    /**
     * 响应信息
     */
    private final String message;
}
