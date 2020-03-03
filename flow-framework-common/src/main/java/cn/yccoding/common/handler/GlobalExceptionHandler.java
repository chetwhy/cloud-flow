package cn.yccoding.common.handler;

import cn.yccoding.common.contants.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.common.vo.R;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author YC
 * @create 2020/3/3
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**-------- 通用异常处理方法 --------**/
    @ResponseBody
    @ExceptionHandler
    public R error(Exception e) {
        e.printStackTrace();
        return R.error();
    }

    /**-------- 指定异常处理方法 --------**/
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public R error(NullPointerException e) {
        e.printStackTrace();
        return R.setResult(ResultCodeEnum.NULL_POINTER);
    }

    /**-------- 自定义定异常处理方法 --------**/
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public R error(CustomException e) {
        e.printStackTrace();
        return R.error().message(e.getMessage()).code(e.getCode());
    }

}
