package cn.yccoding.pay.util;

import cn.yccoding.common.util.SecurityUtils;
import cn.yccoding.pay.sdk.PaymentConstants;
import cn.yccoding.pay.sdk.WXPayUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author YC
 * @since 2020/6/3 18:06
 */
public class SignatureUtils {
    /**
     * 签名校验
     *
     * @param nonceStr
     * @param url
     * @param prepayId
     * @param key
     * @return
     * @throws Exception
     */
    public static Map<String, Object> permissionValidate(String AppID, String nonceStr, String url, String prepayId,
        String key, String jsapiTicket) throws Exception {
        // jssdk权限验证参数
        TreeMap<Object, Object> param = new TreeMap<>();
        Map<String, Object> data = new HashMap<>();
        param.put("appId", AppID);
        String timestamp = String.valueOf(WXPayUtil.getCurrentTimestamp());
        param.put("timestamp", timestamp);// 全小写
        param.put("nonceStr", nonceStr);
        param.put("signature", getSignature(timestamp, nonceStr, url, jsapiTicket));
        data.put("configMap", param);

        // 微信支付权限验证参数
        Map<String, String> payMap = new HashMap<>();
        payMap.put("appId", AppID);
        payMap.put("timeStamp", timestamp);// 驼峰
        payMap.put("nonceStr", nonceStr);
        payMap.put("package", "prepay_id=" + prepayId);
        payMap.put("signType", PaymentConstants.SignType.MD5.toString());
        payMap.put("paySign", WXPayUtil.generateSignature(payMap, key));
        payMap.put("packageStr", "prepay_id=" + prepayId);
        data.put("payMap", payMap);

        return data;
    }

    /**
     * 计算jssdk-config的签名
     * 
     * @param timestamp
     * @param noncestr
     * @param url
     * @return
     */
    public static String getSignature(String timestamp, String noncestr, String url, String jsapiTicket) {
        // 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）
        Map<String, Object> map = new TreeMap<>();
        map.put("jsapi_ticket", jsapiTicket);
        map.put("timestamp", timestamp);
        map.put("noncestr", noncestr);
        map.put("url", url);
        // 使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1
        StringBuilder sb = new StringBuilder();
        Set<String> set = map.keySet();
        for (String key : set) {
            sb.append(key + "=" + map.get(key)).append("&");
        }
        // 去掉最后一个&符号
        String temp = sb.substring(0, sb.length() - 1);
        // 使用sha1加密
        return SecurityUtils.SHA1(temp);
    }

}
