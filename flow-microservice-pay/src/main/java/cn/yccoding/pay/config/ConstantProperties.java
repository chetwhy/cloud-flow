package cn.yccoding.pay.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author YC
 * @create 2020/3/3
 * 公众号配置属性
 */
@Component
public class ConstantProperties implements InitializingBean {

    // 公众号id
    @Value("${we-chat.app-id}")
    private String appId;

    // app密钥
    @Value("${we-chat.app-secret}")
    private String appSecret;

    // 商户号
    @Value("${we-chat.much-id}")
    private String muchId;

    // api密钥
    @Value("${we-chat.api-key}")
    private String apiKey;

    // 公众号注册域名
    @Value("${we-chat.register-domain}")
    private String registerDomain;

    // jsapi支付目录
    @Value("${we-chat.jsapi-payment-auth-dir}")
    private String jsapiPaymentAuthDir;

    // js安全域名
    @Value("${we-chat.js-domain}")
    private String jsDomain;

    // 网页授权域名
    @Value("${we-chat.web-auth-domain}")
    private String webAuthDomain;

    // 证书目录
    @Value("${we-chat.apiclient-cert}")
    private String apiclientCert;

    // 支付回调地址
    @Value("${we-chat.notify-url}")
    private String notifyUrl;

    // 退款回调地址
    @Value("${we-chat.notify-url-refund}")
    private String notifyUrlRefund;

    /**
     * 自动注入静态变量的默认值
     */
    public static String APP_ID;
    public static String APP_SECRET;
    public static String MUCH_ID;
    public static String API_KEY;
    public static String REGISTER_DOMAIN;
    public static String JSAPI_PAYMENT_AUTH_DIR;
    public static String JS_DOMAIN;
    public static String WEB_AUTH_DOMAIN;
    public static String APICLIENT_CERT;
    public static String NOTIFY_URL;
    public static String NOTIFY_URL_REFUND;

    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = this.appId;
        APP_SECRET = this.appSecret;
        MUCH_ID = this.muchId;
        API_KEY = this.apiKey;
        REGISTER_DOMAIN = this.registerDomain;
        JSAPI_PAYMENT_AUTH_DIR = this.jsapiPaymentAuthDir;
        JS_DOMAIN = this.jsDomain;
        WEB_AUTH_DOMAIN = this.webAuthDomain;
        APICLIENT_CERT = this.apiclientCert;
        NOTIFY_URL = this.notifyUrl;
        NOTIFY_URL_REFUND = this.notifyUrlRefund;

    }
}
