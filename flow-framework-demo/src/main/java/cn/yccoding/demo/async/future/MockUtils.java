package cn.yccoding.demo.async.future;

import java.util.Random;
import java.util.UUID;

/**
 * 模拟工具类
 *
 * @author YC
 * @since 2021/1/9
 */
public class MockUtils {

    /**
     * 模拟调用延时
     */
    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int getData() {
        Random random = new Random(1000);
        return random.nextInt(100);
    }

    public static String getInfo() {
        return UUID.randomUUID().toString();
    }
}
