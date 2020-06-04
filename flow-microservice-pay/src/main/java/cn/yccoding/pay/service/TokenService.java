package cn.yccoding.pay.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static cn.yccoding.pay.util.ConstantPropertyUtils.APP_ID;
import static cn.yccoding.pay.util.ConstantPropertyUtils.APP_SECRET;

/**
 * @Author YC
 * @create 2020/6/3
 */
@Service
public class TokenService {

    public static final String WECHAT_ACCESSTOKEN_PROFIX = "wx-accessToken:";
    public static final String WECHAT_ACCESSTOKEN_SUFFIX = ":redis";
    // 获取基本accessToken的接口
    public static final String BASE_ACCESS_TOKEN =
        "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={0}&secret={1}";
    // 页面使用jssdk的凭据
    public static final String BASE_JSAPI_TICKET =
        "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={0}&type=jsapi";
    public static String accessToken;
    public static long expiresTime;
    @Autowired
    private RedisService redisService;
    @Autowired
    private HttpClientService httpClientService;
    @Value("${redis.active}")
    private Boolean redisActive;

    /**
     * 获取全局的access_token
     *
     * @param code
     *            用户唯一标识
     * @return
     */
    public String getAccessToken(String code) {
        if (!redisActive) {
            return getAccessTokenLocal();
        }
        String key = WECHAT_ACCESSTOKEN_PROFIX + code + WECHAT_ACCESSTOKEN_SUFFIX;
        String accessToken = redisService.get(key);
        if (accessToken != null) {
            return accessToken;
        }
        JSONObject obj =
            httpClientService.doGet(MessageFormat.format(BASE_JSAPI_TICKET, APP_ID, APP_SECRET), JSONObject.class);
        String token = obj.getString("access_token"); // 凭据
        Long expiresIn = obj.getLong("expires_in"); // 有效期，单位秒
        if (!StringUtils.isEmpty(token)) {
            redisService.set(key, token, (expiresIn - 5 * 60L) * 1000); // 提前五分钟过期
            return token;
        }
        return null;
    }

    // token存储在内存
    public synchronized String getAccessTokenLocal() {
        // 当accessToken为null或者失效才重新去获取
        if (accessToken == null || LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli() > expiresTime) {
            // 凭据)
            JSONObject obj =
                httpClientService.doGet(MessageFormat.format(BASE_ACCESS_TOKEN, APP_ID, APP_SECRET), JSONObject.class);
            // 凭据
            accessToken = obj.getString("access_token");
            // 有效期
            Long expires_in = obj.getLong("expires_in");
            // 设置凭据的失效时间 = 当前时间+有效期
            expiresTime =
                LocalDateTime.now().toInstant(ZoneOffset.ofHours(8)).toEpochMilli() + ((expires_in - 5 * 60) * 1000);
        }
        return accessToken;
    }

    /**
     * 获取JSSDK的Ticket
     */
    public String getJsApiTicket(String accessToken) {
        // 发起请求到指定的接口
        JSONObject jsonObj = httpClientService.doGet(BASE_JSAPI_TICKET, JSONObject.class, accessToken);
        String ticket = jsonObj.getString("ticket");
        return ticket;
    }
}
