package cn.yccoding.gzh.service.impl;

import cn.yccoding.gzh.util.ConstantPropertyUtils;
import cn.yccoding.gzh.constant.GzhUrlConstant;
import cn.yccoding.gzh.service.CommonGzhService;
import cn.yccoding.gzh.service.RedisService;
import cn.yccoding.gzh.util.RestHttpClientUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Author YC
 * @create 2020/3/6
 */
@Service
public class CommonGzhServiceImpl implements CommonGzhService {

    private static final String WECHAT_ACCESSTOKEN_PROFIX = "wx-accessToken:";
    private static final String WECHAT_ACCESSTOKEN_SUFFIX = ":redis";
    // java内存存储token变量
    public static String localAccessToken;
    // token过期时间
    public static Long expiresTime;
    @Autowired
    private RestHttpClientUtils restHttpClientUtils;
    @Autowired
    private RedisService redisService;

    @Override
    public String getRedisAccessToken() {
        String key = WECHAT_ACCESSTOKEN_PROFIX + ConstantPropertyUtils.APP_ID + WECHAT_ACCESSTOKEN_SUFFIX;
        String accessToken = redisService.get(key);
        if (accessToken != null) {
            return accessToken;
        }
        JSONObject obj = restHttpClientUtils.doGet(MessageFormat.format(GzhUrlConstant.BASE_ACCESS_TOKEN,
            ConstantPropertyUtils.APP_ID, ConstantPropertyUtils.APP_SECRET), JSONObject.class);
        String token = obj.getString("access_token"); // 凭据
        Long expiresIn = obj.getLong("expires_in"); // 有效期，单位秒
        redisService.set(key, token, (expiresIn - 5 * 60L) * 1000); // 提前五分钟过期
        return token;
    }

    @Override
    public String getLocalAccessToken() {
        // 当accessToken为null或者失效才重新去获取
        if (localAccessToken == null
            || LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli() > expiresTime) {
            JSONObject obj = restHttpClientUtils.doGet(MessageFormat.format(GzhUrlConstant.BASE_ACCESS_TOKEN,
                ConstantPropertyUtils.APP_ID, ConstantPropertyUtils.APP_SECRET), JSONObject.class);
            localAccessToken = obj.getString("access_token");
            Long expires_in = obj.getLong("expires_in");
            // 设置凭据的失效时间 = 当前时间+有效期
            expiresTime =
                LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli() + ((expires_in - 60) * 1000);
        }
        return localAccessToken;
    }
}
