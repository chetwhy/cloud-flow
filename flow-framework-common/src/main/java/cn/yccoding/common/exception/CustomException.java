package cn.yccoding.common.exception;

import cn.yccoding.common.contants.ResultCodeEnum;
import lombok.Data;

/**
 * @Author YC
 * @create 2020/3/3 自定义异常
 */
@Data
public class CustomException extends RuntimeException {

    private Integer code;

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "CMSException{" + "code=" + code + ", message=" + this.getMessage() + '}';
    }
}
