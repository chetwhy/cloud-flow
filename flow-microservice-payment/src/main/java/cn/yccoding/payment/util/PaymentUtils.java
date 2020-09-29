package cn.yccoding.payment.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * util
 *
 * @author YC
 * @since 2020/9/28
 */
public class PaymentUtils {

    /**
     * 随机生成订单号
     *
     * @return
     */
    public static String generateRandomOrderNo() {
        // 随机数
        int number = (int) ((Math.random() * 9) * 1000);
        String nowStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        return nowStr + number;
    }
}
