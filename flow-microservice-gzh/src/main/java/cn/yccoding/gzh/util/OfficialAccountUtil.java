package cn.yccoding.gzh.util;

import cn.yccoding.common.util.SecurityUtil;

import java.util.Arrays;

/**
 * @Author YC
 * @create 2020/3/3
 */
public class OfficialAccountUtil {

    public static Boolean checkSignature(String signature, String timestamp, String nonce) {
        // 1 将token、timestamp、nonce三个参数进行字典序排序
        String[] arr = {OfficialAccountProperties.INTERFACE_TOKEN, timestamp, nonce};
        Arrays.sort(arr);
        // 2 将三个参数字符串拼接成一个字符串进行sha1加密
        StringBuffer sb = new StringBuffer();
        Arrays.stream(arr).forEach(sb::append);
        String mySignature = SecurityUtil.SHA1(sb.toString());
        // 3 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
        return mySignature.equals(signature);
    }
}
