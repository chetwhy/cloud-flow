package cn.yccoding.common.util;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Author YC
 * @create 2020/3/6
 * 日期时间工具类
 */
public class DateTimeUtils {

    public static Long getTimeStamp() {
        return LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
    }
}
