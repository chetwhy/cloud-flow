package cn.yccoding.payment.config;

import cn.yccoding.common.base.ResultCodeEnum;
import cn.yccoding.common.exception.CustomException;
import cn.yccoding.payment.wxpay.config.WXPayConfigs;
import cn.yccoding.payment.wxpay.config.WXPayConfigExt;
import cn.yccoding.payment.wxpay.sdk.WXPay;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * config
 *
 * @author YC
 * @since 2020/9/28
 */
@Slf4j
@Configuration
public class WXPaymentConfig {
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

    static {
        WXPayConfigs.init("payment/wxinfo.properties");
        APP_ID = WXPayConfigs.getAppId();
        APP_SECRET = WXPayConfigs.getAppSecret();
        MUCH_ID = WXPayConfigs.getMuchId();
        API_KEY = WXPayConfigs.getApiKey();
        NOTIFY_URL = WXPayConfigs.getNotifyUrl();
        NOTIFY_URL_REFUND = WXPayConfigs.getNotifyUrlRefund();
    }

    /**
     * 注入wxpay实例，同时获取微信支付的信息参数
     * @return
     */
    @Bean
    public WXPay wxPay() {
        WXPay wxPay = null;
        try {
            wxPay = new WXPay(new WXPayConfigExt());
        } catch (Exception e) {
            log.error("创建默认wxpay实列失败:[{}]", e.getMessage());
            throw new CustomException(ResultCodeEnum.WXPAY_INSTANCE_CREATE_FAIL);
        }
        return wxPay;
    }
}
