package cn.yccoding.payment.wxpay.config;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 配置
 *
 * @author YC
 * @since 2020/9/27
 */
@Slf4j
@ToString
public class WXPayConfigs {
    private static Configuration configs;

    // 公众号id
    private static String appId;

    // app密钥
    private static String appSecret;

    // 商户号
    private static String muchId;

    // api密钥
    private static String apiKey;

    // 公众号注册域名
    private static String registerDomain;

    // jsapi支付目录
    private static String jsapiPaymentAuthDir;

    // js安全域名
    private static String jsDomain;

    // 网页授权域名
    private static String webAuthDomain;

    // 证书目录
    private static String apiclientCert;

    // 支付回调地址
    private static String notifyUrl;

    // 退款回调地址
    private static String notifyUrlRefund;

    private WXPayConfigs() {
        // No Constructor
    }

    // 根据文件名读取配置文件，文件后缀名必须为.properties
    public synchronized static void init(String filePath) {
        if (configs != null) {
            return;
        }

        try {
            configs = new PropertiesConfiguration(filePath);
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }

        if (configs == null) {
            throw new IllegalStateException("can`t find file by path:" + filePath);
        }

        appId = configs.getString("appid");
        appSecret = configs.getString("app_secret");
        muchId = configs.getString("mch_id");
        apiKey = configs.getString("api_key");
        registerDomain = configs.getString("register_domain");
        jsapiPaymentAuthDir = configs.getString("jsapi_payment_auth_dir");
        jsDomain = configs.getString("js_domain");
        webAuthDomain = configs.getString("web_auth_domain");
        apiclientCert = configs.getString("apiclient_cert");
        notifyUrl = configs.getString("notify_url");
        notifyUrlRefund = configs.getString("notify_url_refund");

        log.info("配置文件名: " + filePath);
    }

    public static Configuration getConfigs() {
        return configs;
    }

    public static String getAppId() {
        return appId;
    }

    public static String getAppSecret() {
        return appSecret;
    }

    public static String getMuchId() {
        return muchId;
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getRegisterDomain() {
        return registerDomain;
    }

    public static String getJsapiPaymentAuthDir() {
        return jsapiPaymentAuthDir;
    }

    public static String getJsDomain() {
        return jsDomain;
    }

    public static String getWebAuthDomain() {
        return webAuthDomain;
    }

    public static String getApiclientCert() {
        return apiclientCert;
    }

    public static String getNotifyUrl() {
        return notifyUrl;
    }

    public static String getNotifyUrlRefund() {
        return notifyUrlRefund;
    }
}
