package cn.yccoding.common.vo;

import cn.yccoding.common.base.ResultCodeEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

import static cn.yccoding.common.base.ResultCodeEnum.SUCCESS;
import static cn.yccoding.common.base.ResultCodeEnum.UNKNOWN_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

/**
 * @Author YC
 * @create 2020/3/3 统一返回结果类
 */
@Accessors(chain = true)
@Data
public class R {
    /**
     * 响应是否成功
     */
    private boolean success;

    /**
     * 响应码
     */
    private int code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private Map<String, Object> data = new HashMap<>();

    /**
     * 私有构造器
     */
    private R() {

    }

    /**
     * 通用成功
     *
     * @return
     */
    public static R ok() {
        return new R()
                .setSuccess(SUCCESS.isSuccess())
                .setCode(SUCCESS.getCode())
                .setMessage(SUCCESS.getMessage());
    }

    /**
     * 通用成功
     *
     * @return
     */
    public static R error() {
        return new R()
                .setSuccess(UNKNOWN_ERROR.isSuccess())
                .setCode(UNKNOWN_ERROR.getCode())
                .setMessage(UNKNOWN_ERROR.getMessage());
    }

    /**
     * 自定义返回信息
     *
     * @param result
     * @return
     */
    public static R setResult(ResultCodeEnum result) {
        return new R()
                .setSuccess(result.isSuccess())
                .setCode(result.getCode())
                .setMessage(result.getMessage());
    }

    /** ------------使用链式编程，返回类本身-----------**/
    /**
     * 自定义返回数据
     *
     * @param map
     * @return
     */
    public R data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

    /**
     * 通用设置data
     *
     * @param key
     * @param value
     * @return
     */
    public R data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    /**
     * 自定义状态信息
     *
     * @param message
     * @return
     */
    public R message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 自定义状态码
     *
     * @param code
     * @return
     */
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 自定义返回结果
     *
     * @param success
     * @return
     */
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    /**
     * 返回标准实体类
     *
     * @return
     */
    public ResponseEntity<R> buildResponseEntity() {
        HttpStatus httpStatus = this.isSuccess() ?
                OK : BAD_REQUEST;
        return new ResponseEntity<>(this, httpStatus);
    }

    /**
     * 返回标准实体类
     *
     * @return
     */
    public ResponseEntity<R> buildResponseEntity(HttpStatus httpStatus) {
        return new ResponseEntity<>(this, httpStatus);
    }

    /**
     * 返回标准实体类
     *
     * @param headers
     * @return
     */
    public ResponseEntity<R> buildResponseEntity(MultiValueMap<String, String> headers) {
        HttpStatus httpStatus = this.isSuccess() ? OK : BAD_REQUEST;
        return new ResponseEntity<>(this, headers, httpStatus);
    }

    /**
     * 返回标准实体类
     *
     * @return
     */
    public ResponseEntity<R> buildResponseEntity(MultiValueMap<String, String> headers, HttpStatus httpStatus) {
        return new ResponseEntity<>(this, headers, httpStatus);
    }
}
