package cn.yccoding.common.handler;

import cn.yccoding.common.exception.CustomException;
import cn.yccoding.common.util.ExceptionUtils;
import cn.yccoding.common.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import static cn.yccoding.common.base.ResultCodeEnum.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
        // TODO 生产环境不建议打印
        String description = e.getCause().getMessage();
        return new ResponseEntity<>(R.error().data("description", description), BAD_REQUEST);
    }

    /**
     * -------- 指定异常处理方法 --------
     **/
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<R> error(NullPointerException e) {
        log.error(ExceptionUtils.getMessage(e));
        return new ResponseEntity<>(R.setResult(NULL_POINTER), NOT_FOUND);
    }

    /**
     * 参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<R> error(MethodArgumentNotValidException e) {
        log.error(ExceptionUtils.getMessage(e));
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        String description = allErrors.stream().map(x -> x.getDefaultMessage()).reduce((x, y) -> x + "," + y).orElseGet(PARAM_ERROR::getMessage);
        return new ResponseEntity<>(R.setResult(PARAM_ERROR).data("description", description), BAD_REQUEST);
    }

    /**
     * sql执行异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<R> error(SQLIntegrityConstraintViolationException e) {
        log.error(ExceptionUtils.getMessage(e));
        return new ResponseEntity<>(R.setResult(SQL_EXCEPTION), BAD_REQUEST);
    }

    /**
     * 关键词重复
     *
     * @param e
     * @return
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<R> error(DuplicateKeyException e) {
        log.error(ExceptionUtils.getMessage(e));
        return new ResponseEntity<>(R.setResult(DUPLICATE_KEY), BAD_REQUEST);
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
