package cn.yccoding.payment.wxpay.util;

import cn.yccoding.common.util.SecurityUtils;
import cn.yccoding.payment.wxpay.sdk.WXPayConstants;
import cn.yccoding.payment.wxpay.sdk.WXPayUtil;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 微信内H5调起支付
 *
 * @author YC
 * @since 2020/9/27
 */
public class SignatureUtils {

    /**
     * 网页端接口请求参数列表（参数需要重新进行签名计算，参与签名的参数为：appId、timeStamp、nonceStr、package、signType，参数区分大小写。）
     * @param appId
     * @param nonceStr
     * @param url
     * @param prepayId
     * @param key
     * @param jsapiTicket
     * @return
     * @throws Exception
     */
    public static Map<String, Object> permissionValidate(String appId, String nonceStr, String url, String prepayId,
                                                         String key, String jsapiTicket) throws Exception {
        Map<String, Object> data = new HashMap<>();

        // 传入js中getBrandWCPayRequest的参数
        TreeMap<Object, Object> param = new TreeMap<>();
        param.put("appId", appId);
        String timestamp = String.valueOf(WXPayUtil.getCurrentTimestamp());
        param.put("timestamp", timestamp);
        param.put("nonceStr", nonceStr);
        param.put("signature", createSignature(timestamp, nonceStr, url, jsapiTicket));
        data.put("configMap", param);

        // 重新进行签名计算
        Map<String, String> payMap = new HashMap<>();
        payMap.put("appId", appId);
        payMap.put("timeStamp", timestamp);
        payMap.put("nonceStr", nonceStr);
        payMap.put("package", "prepay_id=" + prepayId);
        payMap.put("signType", WXPayConstants.MD5);
        payMap.put("paySign", WXPayUtil.generateSignature(payMap, key));
        data.put("payMap", payMap);

        return data;
    }

    public static String createSignature(String timestamp, String nonceStr, String url, String jsapiTicket) {
        // 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）
        Map<String, Object> map = new TreeMap<>();
        map.put("jsapi_ticket", jsapiTicket);
        map.put("timestamp", timestamp);
        map.put("noncestr", nonceStr);
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
