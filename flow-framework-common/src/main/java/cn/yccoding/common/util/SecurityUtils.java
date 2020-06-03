package cn.yccoding.common.util;

import java.security.MessageDigest;

/**
 * @author : Chet
 * @description : 安全，如签名类方法
 * @date : 2019/11/5
 */
public class SecurityUtils {
    private static final char[] HEX_DIGITS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String encode(String algorithm, String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >>> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }


    public static String MD5(String content){
        return SecurityUtils.encode("MD5", content);
    }

    public static String SHA1(String content){
        return SecurityUtils.encode("SHA1", content);
    }
}
