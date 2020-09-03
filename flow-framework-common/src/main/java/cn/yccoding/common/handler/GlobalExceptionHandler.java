package cn.yccoding.common.handler;

import cn.yccoding.common.contant.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.common.util.ExceptionUtils;
import cn.yccoding.common.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author YC
 * @create 2020/3/3
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * -------- 通用异常处理方法 --------
     **/
    @ExceptionHandler
    public R error(Exception e) {
        //e.printStackTrace();
        log.error(ExceptionUtils.getMessage(e));
        return R.error();
    }

    /**
     * -------- 指定异常处理方法 --------
     **/
    @ExceptionHandler(NullPointerException.class)
    public R error(NullPointerException e) {
        log.error(ExceptionUtils.getMessage(e));
        return R.setResult(ResultCodeEnum.NULL_POINTER);
    }

    /**
     * -------- 自定义定异常处理方法 --------
     **/
    @ExceptionHandler(CustomException.class)
    public R error(CustomException e) {
        log.error(ExceptionUtils.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode());
    }

}
