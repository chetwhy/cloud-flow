package cn.yccoding.payment.wxpay.client;

import cn.hutool.http.HttpUtil;
import cn.yccoding.common.base.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.payment.client.RedisOpsClient;
import cn.yccoding.payment.property.RedisKeyProperties;
import cn.yccoding.payment.wxpay.config.WXPayConfigs;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;

/**
 * client
 *
 * @author YC
 * @since 2020/9/28
 */
@Component
@EnableConfigurationProperties(value = RedisKeyProperties.class)
public class TokenClient {
    /**
     * 页面使用jssdk的凭据
     */
    public static final String JSAPI_TICKET_URL =
            "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";
    /**
     * 获取基本accessToken的接口
     */
    public static final String BASE_ACCESS_TOKEN_URL =
            "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
    @Autowired
    private RedisOpsClient redisOpsClient;
    @Autowired
    private RedisKeyProperties redisKeyProperties;

    /**
     * 获取JSSDK的Ticket
     *
     * @param accessToken
     * @return
     */
    public String getJsApiTicket(String accessToken) {
        // 发起请求到指定的接口
        String respJson = HttpUtil.get(JSAPI_TICKET_URL);
        return JSON.parseObject(respJson).getString("ticket");
    }

    public String getAccessToken() {
        String appId = WXPayConfigs.getAppId();
        String appSecret = WXPayConfigs.getAppSecret();
        return getAccessToken(appId, appSecret);
    }

    /**
     * 获取全局的access_token
     */
    public String getAccessToken(String appId, String appSecret) {
        String key = redisKeyProperties.getPrefix().getWxToken() + appId;
        String accessToken = redisOpsClient.get(key);
        if (accessToken != null) {
            return accessToken;
        }
        // 重新远程获取access token
        String respJson = HttpUtil.get(MessageFormat.format(BASE_ACCESS_TOKEN_URL, appId, appSecret));
        JSONObject ret = JSON.parseObject(respJson);
        // 凭据
        String token = ret.getString("access_token");
        // 有效期，单位秒
        Long expiresIn = ret.getLong("expires_in");
        if (StringUtils.isEmpty(token)) {
            throw new CustomException(ResultCodeEnum.WXPAY_FETCH_TOKEN_FAILED);
        }
        //提前五分钟过期
        long expire = expiresIn > 20 * 60L ? expiresIn - 5 * 60L : expiresIn;
        redisOpsClient.set(key, token, expire, TimeUnit.SECONDS);
        return token;
    }
}
