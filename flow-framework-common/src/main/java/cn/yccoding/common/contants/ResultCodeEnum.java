package cn.yccoding.common.contants;

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
    NULL_POINTER(false,20003,"空指针异常"),
    NO_FILE_CONTENT(false, 30001, "文件内容为空"),
    GET_BLOB_CONTAINER_ERROR(false, 30002, "获取blob容器错误"),
    UPLOAD_BLOB_FAILED(false, 30003, "上传blob数据库失败"),
    DELETE_BLOB_FAILED(false, 30004, "删除blob数据库失败"),
    QUERY_BLOB_FAILED(false, 30005, "查询blob数据库失败"),
    DOWNLOAD_BLOB_FAILED(false, 30006, "下载blob数据库失败"),
    SAVE_SQLSERVER_FAILED(false, 40001, "保存sql server异常"),
    DOWNLOAD_SQLSERVER_FAILED(false, 40002, "下载sql server数据异常"),
    MAIL_SEND_FAILED(false, 410001, "邮件发送失败"),
    CAPTCHA_SEND_FAILED(false, 410002, "验证码发送失败");

    // 响应是否成功
    private Boolean success;
    // 响应状态码
    private Integer code;
    // 响应信息
    private String message;

    ResultCodeEnum(Boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
