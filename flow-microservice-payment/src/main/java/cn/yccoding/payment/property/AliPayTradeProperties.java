package cn.yccoding.payment.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * property
 *
 * @author YC
 * @since 2020/9/29
 */
@Component
@ConfigurationProperties(prefix = "trade.ali-pay")
@Data
public class AliPayTradeProperties {

    /**
     * 二维码
     */
    private AliPayTradeProperties.Qrcode qrcode;
    /**
     * 支付成功回调页面
     */
    private String callbackUrl;

    @Data
    public static class Qrcode{
        /**
         * qrcode存储文件路径设置
         */
        private String storePath;
        /**
         * http访问路径设置
         */
        private String httpBasePath;
    }
}
