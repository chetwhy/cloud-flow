package cn.yccoding.gzh.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author YC
 * @create 2020/3/3
 * 公众号配置属性
 */
@Component
public class ConstantPropertyUtils implements InitializingBean {

    // 公众号id
    @Value("${weChat.APP_ID}")
    private String appId;

    // app密钥
    @Value("${weChat.APP_SECRET}")
    private String appSecret;

    // 微信接入——接口配置信息
    @Value("${weChat.INTERFACE_URL}")
    private String interfaceUrl;

    // 微信接入——接口配置信息
    @Value("${weChat.INTERFACE_TOKEN}")
    private String interfaceToken;

    // js安全域名
    @Value("${weChat.JS_DOMAIN}")
    private String jsDomain;

    // 网页授权域名
    @Value("${weChat.WEB_AUTH_DOMAIN}")
    private String webAuthDomain;

    /**
     * 自动注入静态变量的默认值
     */
    public static String APP_ID;
    public static String APP_SECRET;
    public static String INTERFACE_URL;
    public static String INTERFACE_TOKEN;
    public static String JS_DOMAIN;
    public static String WEB_AUTH_DOMAIN;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = this.appId;
        APP_SECRET = this.appSecret;
        INTERFACE_URL = this.interfaceUrl;
        INTERFACE_TOKEN = this.interfaceToken;
        JS_DOMAIN = this.jsDomain;
        WEB_AUTH_DOMAIN = this.webAuthDomain;
    }
}
