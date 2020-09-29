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
    SQL_EXCEPTION(false,30004,  "sql执行异常"  ),
    DUPLICATE_KEY(false,30005,  "关键字重复"  ),
    NOTFOUND_USER(false, 30006, "用户名不存在"),
    UNMATCH_UNAME_PWD(false, 30006, "用户名或密码错误"),

    JWT_EXPIRED(false, 31001, "JWT验证失败:token已经过期"),
    JWT_UNSUPPORTED(false, 31001, "JWT验证失败:token格式不支持"),
    JWT_MALFORMED(false, 31001, "JWT验证失败:token格式错误"),
    JWT_SIGNATURE(false, 31001, "JWT验证失败:token签名错误"),
    JWT_ILLEGAL_ARGUMENT(false, 31001, "JWT验证失败:token非法参数"),

    /**
     * member module
     */
    EXISTED_USER(false, 41000, "用户已存在"),
    INCORRECT_OPTCODE(false, 41001, "验证码未过期"),
    UNMATCH_OPTCODE(false, 41002, "验证码不匹配"),
    UNAUTHORIZED(false, 41003, "未登录或token已经过期"),
    AUTHORIZATION_HEADER_IS_EMPTY(false, 41004, "请求头无授权信息"),
    AUTHORIZATION_INVALID(false,41005, "无效的token信息"),
    TOKEN_EXPIRED(false, 41006, "token已过期"),
    ACCESS_FORBIDEN(false, 41007, "没有访问权限"),

    /**
     * wechat pay
     */
    WXPAY_INSTANCE_CREATE_FAIL(false, 51001, "微信支付实例创建失败"),
    WXPAY_REQUEST_FAILED(false, 51002, "微信支付接口请求失败"),
    WXPAY_FETCH_TOKEN_FAILED(false, 51003, "微信公众号获取access token失败"),
    ALIPAY_CALLBACK_CHECK_FAILED(false, 52001, "支付宝支付回调结果验签失败"),;

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
