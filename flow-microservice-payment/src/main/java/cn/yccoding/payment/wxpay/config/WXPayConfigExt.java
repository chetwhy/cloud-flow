package cn.yccoding.payment.wxpay.config;

import cn.yccoding.payment.wxpay.sdk.IWXPayDomain;
import cn.yccoding.payment.wxpay.sdk.WXPayConfig;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * ext
 *
 * @author YC
 * @since 2020/9/28
 */
@Component
public class WXPayConfigExt extends WXPayConfig {

    /**
     * 证书字节流
     */
    private byte[] certData;

    @Override
    public String getAppID() {
        return WXPayConfigs.getAppId();
    }

    @Override
    public String getMchID() {
        return WXPayConfigs.getMuchId();
    }

    @Override
    public String getKey() {
        return WXPayConfigs.getApiKey();
    }

    @Override
    public InputStream getCertStream() {
        ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }
}
