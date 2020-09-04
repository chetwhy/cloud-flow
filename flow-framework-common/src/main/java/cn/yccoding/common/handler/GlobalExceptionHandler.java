package cn.yccoding.common.handler;

import cn.yccoding.common.base.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.common.util.ExceptionUtils;
import cn.yccoding.common.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<R> error(Exception e) {
        log.error(ExceptionUtils.getMessage(e));
        return new ResponseEntity<>(R.error(), HttpStatus.BAD_REQUEST);
    }

    /**
     * -------- 指定异常处理方法 --------
     **/
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<R> error(NullPointerException e) {
        log.error(ExceptionUtils.getMessage(e));
        return new ResponseEntity<>(R.setResult(ResultCodeEnum.NULL_POINTER),HttpStatus.NOT_FOUND);
    }

    /**
     * -------- 自定义定异常处理方法 --------
     **/
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<R> error(CustomException e) {
        log.error(ExceptionUtils.getMessage(e));
        return R.error().message(e.getMessage()).code(e.getCode()).buildResponseEntity();
    }

}
